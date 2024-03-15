package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import br.com.fiap.techchallenge.carrinho.entities.Produto;
import br.com.fiap.techchallenge.carrinho.entities.enums.Status;
import br.com.fiap.techchallenge.carrinho.feignclients.ProdutoFeignClient;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoFinalizadoRepository;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoFinalizadoRepository carrinhoFinalizadoRepository;
    private final ProdutoFeignClient produtoFeignClient;

    private static String getBearerToken(JwtAuthenticationToken token) {
        return format("Bearer %s", token.getToken().getTokenValue());
    }

    private static DecodedJWT getDecodedJwt(Jwt token) {
        return JWT.decode(token.getTokenValue());
    }

    public CarrinhoAberto findCarrinhoOpen(JwtAuthenticationToken token) {
        DecodedJWT decodedJwt = getDecodedJwt(token.getToken());
        return carrinhoRepository.findById(decodedJwt.getSubject())
                .orElseThrow(() -> new NoSuchElementException("Não há carrinho em aberto"));
    }

    public CarrinhoAberto criarNovoCarrinho(String usuarioId) {
        carrinhoRepository.findById(usuarioId).ifPresent(carrinhoRepository::delete);
        return carrinhoRepository.save(new CarrinhoAberto(usuarioId));
    }

    public CarrinhoAberto addItems(JwtAuthenticationToken token, Produto produto) {
        CarrinhoAberto carrinhoAberto = findCarrinhoOpen(token);
        checarSeTemEstoque(getBearerToken(token), produto);

        produtoExistenteNoCarrinhoSomarQuantidade(carrinhoAberto, produto);
        return carrinhoRepository.save(carrinhoAberto);
    }

    public CarrinhoFinalizado efetuandoCompraDoCarrrinho(JwtAuthenticationToken jwtToken) {
        CarrinhoAberto carrinhoAberto = findCarrinhoOpen(jwtToken);

        String bearerToken = getBearerToken(jwtToken);
        carrinhoAberto.getProdutos().forEach(produto -> {
            checarSeTemEstoque(bearerToken, produto);
            produtoFeignClient.updateEstoqueProduto(
                    bearerToken, produto.getProdutoId(), (produto.getQuantidade() * -1));
        });

        CarrinhoFinalizado carrinhoFinalizado = new CarrinhoFinalizado(carrinhoAberto);
        carrinhoFinalizado.setDataDoPagamento(LocalDateTime.now());
        carrinhoFinalizado.setStatusPagamento(true);
        carrinhoFinalizado.setStatusDoPedido(Status.PAGAMENTOAPROVADO);

        carrinhoRepository.deleteById(carrinhoAberto.getUsuarioId());
        return carrinhoFinalizadoRepository.save(carrinhoFinalizado);
    }

    private void checarSeTemEstoque(String token, Produto produto) {
        if (Boolean.FALSE.equals(produtoFeignClient.checarSeHaEstoque(token, produto))) {
            throw new NoSuchElementException("Produto sem estoque");
        }
    }

    private void produtoExistenteNoCarrinhoSomarQuantidade(CarrinhoAberto carrinhoAberto, Produto produto) {
        Map<String, Produto> produtoMap = carrinhoAberto.getProdutos()
                .stream().collect(Collectors.toMap(Produto::getProdutoId, Function.identity()));

        if (produtoMap.containsKey(produto.getProdutoId())) {
            produtoMap.get(produto.getProdutoId()).addQuantidade(produto.getQuantidade());
        } else {
            carrinhoAberto.addProduto(produto);
        }
    }
}

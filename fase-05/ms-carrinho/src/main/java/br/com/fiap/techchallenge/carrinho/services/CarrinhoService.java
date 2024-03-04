package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.entities.enums.Status;
import br.com.fiap.techchallenge.carrinho.functions.EstoquePedidoProducer;
import static br.com.fiap.techchallenge.carrinho.functions.webRequest.WebClientLinkRequest.requisitionGeneric;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoFinalizadoRepository;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoFinalizadoRepository carrinhoFinalizadoRepository;
    private final EstoquePedidoProducer estoquePedidoProducer;

    public CarrinhoService(CarrinhoRepository carrinhoRepository, CarrinhoFinalizadoRepository carrinhoFinalizadoRepository, EstoquePedidoProducer estoquePedidoProducer) {
        this.carrinhoRepository = carrinhoRepository;
        this.carrinhoFinalizadoRepository = carrinhoFinalizadoRepository;
        this.estoquePedidoProducer = estoquePedidoProducer;
    }

    // <>------ Services ----------------------------------------------------------------
    public CarrinhoAberto findCarrinhoOpen(String usuarioId) {
        Optional<CarrinhoAberto> carrinhoAbertoEncontrado = carrinhoRepository.findById(usuarioId);

        return carrinhoAbertoEncontrado.orElseThrow(() -> new NoSuchElementException("Não há carrinho em aberto"));
    }

    public CarrinhoAberto addItemsOuCriarCarrinho(String usuarioId, Produtos produto) {

        Optional<CarrinhoAberto> carrinhoAbertoEncontrado = carrinhoRepository.findById(usuarioId);

        if (!carrinhoAbertoEncontrado.isEmpty()) {
            var carrinho = carrinhoAbertoEncontrado.get();
            produtoExistenteNoCarrinhoSomarQuantidade(carrinho, produto);

            return carrinhoRepository.save(carrinho);
        } else {
            CarrinhoAberto novoCarrinhoAberto = new CarrinhoAberto(usuarioId, produto);

            return carrinhoRepository.save(novoCarrinhoAberto);
        }
    }

    @Transactional
    public CarrinhoFinalizado efetuandoCompraDoCarrrinho(String usuarioId) {
       var carrinhoAbertoOptional = carrinhoRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Não ha carrinho aberto"));

        checarSeTemEstoque(carrinhoAbertoOptional);

        carrinhoAbertoOptional.getProdutos().forEach(
                produto -> produto.setQuantidade(produto.getQuantidade() - (produto.getQuantidade() *2))); //Negativando a quantidade para simular a compra

        carrinhoAbertoOptional.getProdutos().forEach(produto -> estoquePedidoProducer.removerEstoque(produto));

        CarrinhoFinalizado carrinhoFinalizado =
                new CarrinhoFinalizado(carrinhoAbertoOptional);

        carrinhoFinalizado.setDataDoPagamento(LocalDateTime.now());
        carrinhoFinalizado.setStatusPagamento(true);
        carrinhoFinalizado.setStatusDoPedido(Status.PAGAMENTOAPROVADO);

        var pedidoEfetuado = carrinhoFinalizadoRepository.save(carrinhoFinalizado);

        carrinhoRepository.deleteById(pedidoEfetuado.getUsuarioId());

        return pedidoEfetuado;
    }

    public void deletarCarrinho(String usuarioId) {

        carrinhoRepository.deleteById(usuarioId);
    }


    // <>----- Metodos Complementares Privados Apenas a classe pode usar
    private void checarSeTemEstoque(CarrinhoAberto carrinhoAberto) {
        carrinhoAberto.getProdutos().stream()
                .forEach(produto -> {
                    var temEstoque = requisitionGeneric("/produtos/checarsetemestoque", HttpMethod.GET, produto, Boolean.class);
                    if (!temEstoque) {
                        throw new NoSuchElementException("Produto sem estoque");
                    }
                });
    }

    private void produtoExistenteNoCarrinhoSomarQuantidade(CarrinhoAberto carrinhoAberto, Produtos produto) {

        boolean exists = carrinhoAberto.getProdutos().stream()
                .filter(produtos -> produtos.getProdutoId() != null)
                .anyMatch(produtosNoCarrinho ->
                        produtosNoCarrinho.getProdutoId().equals(produto.getProdutoId()));

        if (!exists) {
            carrinhoAberto.addProduto(produto);
        } else {
            carrinhoAberto.getProdutos().forEach(produtosNoCarrinho -> {
                if (Objects.equals(produtosNoCarrinho.getProdutoId(), produto.getProdutoId())) {
                    produtosNoCarrinho.addQuantidade(produto.getQuantidade());
                }
            });
        }
    }
}

//
//spring.data.mongodb.database=ms-carrinho
//        spring.data.mongodb.authentication-database=admin
//        spring.data.mongodb.username=mongodb
//        spring.data.mongodb.password=mongodb
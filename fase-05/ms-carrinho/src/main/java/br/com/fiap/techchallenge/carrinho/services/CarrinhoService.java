package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.entities.enums.Status;

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

    public CarrinhoService(CarrinhoRepository carrinhoRepository, CarrinhoFinalizadoRepository carrinhoFinalizadoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.carrinhoFinalizadoRepository = carrinhoFinalizadoRepository;
    }

    // <>------ Services ----------------------------------------------------------------
    public CarrinhoAberto findCarrinhoOpen(String usuarioId) {
        Optional<CarrinhoAberto> carrinhoAbertoEncontrado = carrinhoRepository.findById(usuarioId);

        return carrinhoAbertoEncontrado.orElseThrow(() -> new NoSuchElementException("Não há carrinho em aberto"));
    }

    public CarrinhoAberto addItemsOuCriarCarrinho(String usuarioId, Produtos produto) {
        checarSeTemEstoque(produto);
        Optional<CarrinhoAberto> carrinhoAbertoEncontrado = carrinhoRepository.findById(usuarioId);

        if (!carrinhoAbertoEncontrado.isEmpty() && produto.getQuantidade() > 0) {
            var carrinho = carrinhoAbertoEncontrado.get();

            produtoExistenteNoCarrinhoSomarQuantidade(carrinho, produto);

            return carrinhoRepository.save(carrinho);
        } else if(carrinhoAbertoEncontrado.isEmpty() && produto.getQuantidade() > 0){
            CarrinhoAberto novoCarrinhoAberto = new CarrinhoAberto(usuarioId, produto);

            return carrinhoRepository.save(novoCarrinhoAberto);
        } else {
            throw new NoSuchElementException("Produto sem estoque");
        }
    }

    @Transactional
    public CarrinhoFinalizado efetuandoCompraDoCarrrinho(String usuarioId) {
        var carrinhoAbertoOptional = carrinhoRepository.findById(usuarioId)
                .orElseThrow(() -> new NoSuchElementException("Não ha carrinho aberto"));


        carrinhoAbertoOptional.getProdutos()
                .forEach(produto -> {
                            checarSeTemEstoque(produto);
                            negativaOuPositivaQuantidade(produto); //Negativando a quantidade para simular a compra
                        });

        carrinhoAbertoOptional.getProdutos()
                .forEach(produto -> {
                    String requisitionPath = ("/produtos/" + produto.getProdutoId() + "?alteracaoEstoque=" + produto.getQuantidade());
                    requisitionGeneric(requisitionPath, HttpMethod.PUT, null, Object.class);

                    negativaOuPositivaQuantidade(produto); //Positivando Quantidade pra salvar carrinho finalizado
                });

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
    private void checarSeTemEstoque(Produtos produto) {

        var temEstoque = requisitionGeneric("/produtos/checarsetemestoque", HttpMethod.GET, produto, Boolean.class);
        if (!temEstoque) {
            throw new NoSuchElementException("Produto sem estoque");
        }

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

    private void negativaOuPositivaQuantidade(Produtos produto) {
        if (produto.getQuantidade() < 0) {
            // If quantity is negative, make it positive for creating finished cart with quantity
            produto.setQuantidade(produto.getQuantidade() - (produto.getQuantidade() * 2));
        } else {
            // If quantity is positive, negate it to deduct from stock
            produto.setQuantidade(produto.getQuantidade() - (produto.getQuantidade() * 2));
        }
    }
}

//
//spring.data.mongodb.database=ms-carrinho
//        spring.data.mongodb.authentication-database=admin
//        spring.data.mongodb.username=mongodb
//        spring.data.mongodb.password=mongodb
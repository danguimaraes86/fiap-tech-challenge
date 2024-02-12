package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.entities.enums.Status;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import br.com.fiap.techchallenge.carrinho.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    private final PedidoRepository pedidoRepository;
    public CarrinhoService(CarrinhoRepository carrinhoRepository, PedidoRepository pedidoRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    // <>------ Services ----------------------------------------------------------------
    public CarrinhoAberto findCarrinhoOpen(String usuarioId){
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

    public CarrinhoFinalizado efetuandoCompraDoCarrrinho(String usuarioId){
        var carrinhoAbertoOptional = carrinhoRepository.findById(usuarioId);

        if (carrinhoAbertoOptional.isPresent()){
            CarrinhoFinalizado carrinhoFinalizado =
                    new CarrinhoFinalizado(carrinhoAbertoOptional.get());

            carrinhoFinalizado.setDataDoPagamento(LocalDateTime.now());
            carrinhoFinalizado.setStatusPagamento(true);
            carrinhoFinalizado.setStatusDoPedido(Status.PAGAMENTOAPROVADO);

            var pedidoEfetuado = pedidoRepository.save(carrinhoFinalizado.getClass());

            carrinhoRepository.deleteById(pedidoEfetuado.getUsuarioId());

            return pedidoEfetuado;

        } else {
            throw new RuntimeException("Erro ao efetuar pedido");
        }
    }

    public void deletarCarrinho(String usuarioId) {

        carrinhoRepository.deleteById(usuarioId);
    }



    // <>----- Metodos Complementares Privados Apenas a classe pode usar
    private void produtoExistenteNoCarrinhoSomarQuantidade(CarrinhoAberto carrinhoAberto, Produtos produto) {

        boolean exists = carrinhoAberto.getProdutos().stream()
                .anyMatch(produtosNoCarrinho ->
                        produtosNoCarrinho.getProdutoId().equals(produto.getProdutoId()));

        if (!exists) {
            carrinhoAberto.addProduto(produto);
        } else {
            carrinhoAberto.getProdutos().forEach(produtosNoCarrinho -> {
                if (produtosNoCarrinho.getProdutoId() == produto.getProdutoId()) {
                    produtosNoCarrinho.addQuantidade(produto.getQuantidade());
                }
            });
        }
    }
}
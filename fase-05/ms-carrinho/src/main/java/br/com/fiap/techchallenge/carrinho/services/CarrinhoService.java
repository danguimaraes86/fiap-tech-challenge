package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CarrinhoService {
    private final CarrinhoRepository carrinhoRepository;
    public CarrinhoService(CarrinhoRepository carrinhoRepository) {
        this.carrinhoRepository = carrinhoRepository;
    }

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

    public void deletarCarrinho(String usuarioId) {

        carrinhoRepository.deleteById(usuarioId);
    }

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
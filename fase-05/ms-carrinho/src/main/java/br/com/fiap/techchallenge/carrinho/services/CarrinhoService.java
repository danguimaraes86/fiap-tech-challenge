package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.Carrinho;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository) {
        this.carrinhoRepository = carrinhoRepository;
    }

    public Carrinho addItemsOuCriarCarrinho(Long usuarioId, Produtos produto) {

        Optional<Carrinho> carrinhoAbertoEncontrado = carrinhoRepository.findById(usuarioId);

        if (carrinhoAbertoEncontrado.isEmpty()) {
            Carrinho novoCarrinho = new Carrinho(usuarioId, produto);

            return carrinhoRepository.save(novoCarrinho);
        } else if(carrinhoAbertoEncontrado.isPresent()){

            produtoExistenteNoCarrinhoSomarQuantidade(carrinhoAbertoEncontrado, produto);
            }

        }
    }

        public void deletarCarrinho(Long carrinhoId){

            carrinhoRepository.deleteById(carrinhoId);
        }

        private void produtoExistenteNoCarrinhoSomarQuantidade(Carrinho carrinho, Produtos produto){
            var exixteOProdutoNoCarrinho = carrinho.getProdutos().stream()
                    .filter(produtosNoCarrinho ->
                            produtosNoCarrinho.getProdutoId() == produto.getProdutoId())
                    .findAny();

            if ((exixteOProdutoNoCarrinho.isEmpty()) || (exixteOProdutoNoCarrinho == null)) {
                carrinho.addProduto(produto);
            } else {
                carrinho.getProdutos().forEach(produtosNoCarrinho -> {
                    if (produtosNoCarrinho.getProdutoId() == produto.getProdutoId()) {
                        produtosNoCarrinho.addQuantidade(produto.getQuantidade());
                    }
                });

        }
    }
}
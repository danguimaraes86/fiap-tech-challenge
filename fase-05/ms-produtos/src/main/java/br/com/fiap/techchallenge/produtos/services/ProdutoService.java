package br.com.fiap.techchallenge.produtos.services;

import br.com.fiap.techchallenge.produtos.exceptions.domain.EstoqueInsuficienteException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoJaCadastradoException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoNaoEncontradoException;
import br.com.fiap.techchallenge.produtos.models.Produto;
import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoRequestDTO;
import br.com.fiap.techchallenge.produtos.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static br.com.fiap.techchallenge.produtos.exceptions.domain.MensagensErro.*;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<Produto> findAllProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto findProdutoById(String id) {
        return produtoRepository.findById(id).orElseThrow(() ->
                new ProdutoNaoEncontradoException(String.format(PRODUTO_NAO_ENCONTRADO.getMensagem(), id))
        );
    }

    public Page<Produto> findProdutosNomeDescricao(Pageable pageable, String nome, String descricao) {
        return produtoRepository.findByNomeContainsIgnoreCaseAndDescricaoContainsIgnoreCase(pageable, nome, descricao);
    }

    public Boolean checarSeHaEstoque(ProdutoRequestDTO produtoDTO) {
        Produto produtoFound = findProdutoById(produtoDTO.produtoId());

        return produtoFound.getEstoque() >= produtoDTO.quantidade();
    }

    public Produto insertProduto(ProdutoDTO produtoDTO) {
        if (produtoRepository.findByNomeIgnoreCase(produtoDTO.nome()).isPresent()) {
            throw new ProdutoJaCadastradoException(String.format(NOME_JA_CADASTRADO.getMensagem(), produtoDTO.nome()));
        }
        return produtoRepository.save(
                new Produto(
                        produtoDTO.nome(), produtoDTO.descricao(),
                        produtoDTO.preco().orElse(0.0),
                        produtoDTO.estoque().orElse(0L)
                )
        );
    }

    public Produto updateProdutoEstoque(String id, Long alteracaoEstoque) {
        Produto produto = findProdutoById(id);
        validateEstoque(produto.getEstoque(), alteracaoEstoque);
        produto.updateEstoqe(alteracaoEstoque);
        return produtoRepository.save(produto);
    }

    private void validateEstoque(Long estoque, Long alteracaoEstoque) {
        if (estoque + alteracaoEstoque < 0) {
            throw new EstoqueInsuficienteException(
                    String.format(ESTOQUE_INSUFICIENTE.getMensagem(), alteracaoEstoque, estoque)
            );
        }
    }

}

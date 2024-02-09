package br.com.fiap.techchallenge.produtos.services;

import br.com.fiap.techchallenge.produtos.exceptions.domain.EstoqueInsuficienteException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.PrecoAbaixoZeroException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoJaCadastradoException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoNaoEncontradoException;
import br.com.fiap.techchallenge.produtos.model.Produto;
import br.com.fiap.techchallenge.produtos.model.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.produtos.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Produto> buscaProdutosNomeDescricao(Pageable pageable, String nome, String descricao) {
        return produtoRepository.findByNomeContainsIgnoreCaseAndDescricaoContainsIgnoreCase(pageable, nome, descricao);
    }

    public Produto insertProduto(ProdutoDTO produtoDTO) {
        if (produtoRepository.findByNomeIgnoreCase(produtoDTO.nome()).isPresent()) {
            throw new ProdutoJaCadastradoException(String.format(NOME_JA_CADASTRADO.getMensagem(), produtoDTO.nome()));
        }
        if (produtoDTO.preco() < 0) {
            throw new PrecoAbaixoZeroException(PRECO_ABAIXO_ZERO.getMensagem());
        }
        return produtoRepository.save(new Produto(produtoDTO.nome(), produtoDTO.descricao(), produtoDTO.preco()));
    }

    public Produto updateProdutoEstoque(String id, Long alteracaoEstoque) {
        Produto produto = findProdutoById(id);
        if (produto.getEstoque() + alteracaoEstoque < 0) {
            throw new EstoqueInsuficienteException(
                    String.format(ESTOQUE_INSUFICIENTE.getMensagem(), alteracaoEstoque, produto.getEstoque())
            );
        }
        produto.updateEstoqe(alteracaoEstoque);
        return produtoRepository.save(produto);
    }

    public void deleteProduto(String id) {
        Produto produto = findProdutoById(id);
        produtoRepository.delete(produto);
    }
}

package br.com.fiap.techchallenge.produtos.services;

import br.com.fiap.techchallenge.produtos.exceptions.ProdutoNaoEncontradoException;
import br.com.fiap.techchallenge.produtos.model.Produto;
import br.com.fiap.techchallenge.produtos.model.dtos.ProdutoDTO;
import br.com.fiap.techchallenge.produtos.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Page<Produto> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto findById(String id) {
        return produtoRepository.findById(id).orElseThrow(() ->
                new ProdutoNaoEncontradoException(String.format("produto_id %s não encontrado", id))
        );
    }

    public Produto insert(ProdutoDTO produtoDTO) {
        return produtoRepository.save(
                new Produto(produtoDTO.nome(), produtoDTO.descricao(), produtoDTO.preco())
        );
    }

    public Produto updateEstoque(String id, Long alteracaoEstoque) {
        Produto produto = findById(id);
        produto.updateEstoqe(alteracaoEstoque);
        return produtoRepository.save(produto);
    }
}

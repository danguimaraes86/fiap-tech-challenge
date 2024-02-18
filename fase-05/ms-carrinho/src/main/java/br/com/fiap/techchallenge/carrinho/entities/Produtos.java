package br.com.fiap.techchallenge.carrinho.entities;

import org.springframework.stereotype.Service;

@Service
public class Produtos {
    private String produtoId;
    private Long estoque;

    public Produtos(String produtoId, Long estoque) {
        this.produtoId = produtoId;
        this.estoque = estoque;
    }
    public Produtos() {
    }

    public String getProdutoId() {
        return produtoId;
    }

    public Long getEstoque() {
        return estoque;
    }
    public Produtos setEstoque(Long estoque) {
        this.estoque = estoque;
        return this;
    }

    public void addQuantidade(Long quantidadeParaAdicionar){
        this.estoque += quantidadeParaAdicionar;
    }
}

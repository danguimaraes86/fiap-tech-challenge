package br.com.fiap.techchallenge.carrinho.entities;

import org.springframework.stereotype.Service;

@Service
public class Produtos {
    private String produtoId;
    private Long quantidade;

    public Produtos(String produtoId, Long quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }
    public Produtos() {
    }

    public String getProdutoId() {
        return produtoId;
    }

    public Long getQuantidade() {
        return quantidade;
    }
    public Produtos setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Produtos addQuantidade(Long quantidadeParaAdicionar){
        this.quantidade += quantidadeParaAdicionar;
        return this;
    }
}

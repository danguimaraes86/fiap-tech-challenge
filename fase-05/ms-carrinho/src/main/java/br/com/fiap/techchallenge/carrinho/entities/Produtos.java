package br.com.fiap.techchallenge.carrinho.entities;

import org.springframework.stereotype.Service;

@Service
public class Produtos {

    private Long produtoId;
    private Long quantidade;

    public Produtos(Long produtoId, Long quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }
    public Produtos() {
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public Long getQuantidade() {
        return quantidade;
    }
    public Produtos setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void addQuantidade(Long quantidadeParaAdicionar){
        this.quantidade += quantidadeParaAdicionar;
    }
}

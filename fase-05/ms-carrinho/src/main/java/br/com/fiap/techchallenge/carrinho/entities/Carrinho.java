package br.com.fiap.techchallenge.carrinho.entities;


import java.util.Set;


public class Carrinho {

    private Long id;
    private Long usuarioId;
    private Set<Produtos> produtos;

    public Carrinho(Long id, Long usuarioId, Set<Produtos> produtos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.produtos = produtos;
    }

    public Carrinho(Long usuarioId, Produtos produtos) {
        this.usuarioId = usuarioId;
        this.produtos.add(produtos);
    }

    public Long getUsuarioId() {
        return usuarioId;
    }


    public Set<Produtos> getProdutos() {
        return produtos;
    }
    public Carrinho setProdutos(Set<Produtos> produtos) {
        this.produtos = produtos;
        return this;
    }

    public void addProduto(Produtos produtos){
        this.produtos.add(produtos);
    }
}

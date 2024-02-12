package br.com.fiap.techchallenge.carrinho.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;


// Carrinho aberto, compra ainda não finalizada, como não pode te mais de 1 carrinho aberto por usuario
// Então o ID é o propio UsuarioID
@Document
public class CarrinhoAberto {
    @Id
    private String usuarioId;
    private Set<Produtos> produtos;

    // <>------ Construtores
    public CarrinhoAberto(String usuarioId, Produtos produtos) {
        this.usuarioId = usuarioId;
        this.produtos = new HashSet<>();
        this.produtos.add(produtos);
    }
    public CarrinhoAberto(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    // <>------ GETs e SETs
    public Set<Produtos> getProdutos() {
        return produtos;
    }
    public CarrinhoAberto setProdutos(Set<Produtos> produtos) {
        this.produtos = produtos;
        return this;
    }



    // <>----- Serviços Internos
    public void addProduto(Produtos produtos){
        this.produtos.add(produtos);
    }
}

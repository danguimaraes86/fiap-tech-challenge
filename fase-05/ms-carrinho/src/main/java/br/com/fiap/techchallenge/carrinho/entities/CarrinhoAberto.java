package br.com.fiap.techchallenge.carrinho.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document
public class CarrinhoAberto {
    @Id
    private String usuarioId;
    @Setter
    private Set<Produto> produtos = new HashSet<>();

    public CarrinhoAberto(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void addProduto(Produto produto) {
        this.produtos.add(produto);
    }
}

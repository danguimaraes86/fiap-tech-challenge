package br.com.fiap.techchallenge.produtos.models;

import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String nome;
    @NotBlank
    private String descricao;
    @PositiveOrZero
    private Double preco;
    @PositiveOrZero
    private Long estoque;

    public Produto(String nome, String descricao, Double preco, Long estoque) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }

    public void updateEstoqe(Long alteracaoEstoque) {
        this.estoque -= alteracaoEstoque;
    }

    public ProdutoDTO toProdutoDTO() {
        return new ProdutoDTO(
                this.id, this.nome, this.descricao, Optional.of(this.preco), Optional.of(this.estoque)
        );
    }
}

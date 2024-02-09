package br.com.fiap.techchallenge.produtos.model;

import br.com.fiap.techchallenge.produtos.model.dtos.ProdutoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String nome;
    private String descricao;
    @PositiveOrZero
    private Double preco;
    @PositiveOrZero
    private Long estoque = 0L;

    public Produto(String nome, String descricao, Double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public void updateEstoqe(Long alteracaoEstoque) {
        this.estoque += alteracaoEstoque;
    }

    public ProdutoDTO toProdutoDTO() {
        return new ProdutoDTO(
                this.id, this.nome, this.descricao, this.preco, this.estoque
        );
    }
}

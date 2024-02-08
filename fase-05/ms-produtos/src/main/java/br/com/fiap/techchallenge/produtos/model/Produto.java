package br.com.fiap.techchallenge.produtos.model;

import br.com.fiap.techchallenge.produtos.exceptions.EstoqueInsuficienteException;
import br.com.fiap.techchallenge.produtos.model.dtos.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal preco;
    private Long estoque = 0L;

    public Produto(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public void updateEstoqe(Long alteracaoEstoque) {
        if (this.estoque + alteracaoEstoque < 0) {
            throw new EstoqueInsuficienteException(
                    String.format("o pedido atual é de %d. estoque atual é %d.", alteracaoEstoque, this.estoque)
            );
        }
        this.estoque += alteracaoEstoque;
    }

    public ProdutoDTO toProdutoDTO() {
        return new ProdutoDTO(
                this.id, this.nome, this.descricao, this.preco, this.estoque
        );
    }
}

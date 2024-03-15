package br.com.fiap.techchallenge.carrinho.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Produto {

    private String produtoId;
    @Setter
    private Long quantidade;

    public void addQuantidade(Long quantidadeParaAdicionar) {
        this.quantidade += quantidadeParaAdicionar;
    }
}

package br.com.fiap.techchallenge.carrinho.entities;


import br.com.fiap.techchallenge.carrinho.entities.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document
public class CarrinhoFinalizado {

    @Id
    private Long id;
    private String usuarioId;
    private Set<Produtos> produtos;
    private LocalDateTime dataDoPagamento;
    private Boolean statusPagamento;
    private Status statusDoPedido;

    // <>------ Construtores
    public CarrinhoFinalizado(Long id, String usuarioId, Produtos produtos, LocalDateTime dataDoPagamento, Boolean statusPagamento, Status statusDoPedido) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.produtos = new HashSet<>();
        this.produtos.add(produtos);
        this.dataDoPagamento = dataDoPagamento;
        this.statusPagamento = statusPagamento;
        this.statusDoPedido = statusDoPedido;
    }
    public CarrinhoFinalizado(CarrinhoAberto carrinhoAberto){
        this.usuarioId = carrinhoAberto.getUsuarioId();
        this.produtos = carrinhoAberto.getProdutos();
    }


    // <>------ GETs e SETs
    public String getUsuarioId() {
        return usuarioId;
    }

    public CarrinhoFinalizado setDataDoPagamento(LocalDateTime dataDoPagamento) {
        this.dataDoPagamento = dataDoPagamento;
        return this;
    }

    public CarrinhoFinalizado setStatusPagamento(Boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
        return this;
    }

    public CarrinhoFinalizado setStatusDoPedido(Status statusDoPedido) {
        this.statusDoPedido = statusDoPedido;
        return this;
    }
}

package br.com.fiap.techchallenge.carrinho.entities;

import br.com.fiap.techchallenge.carrinho.entities.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Document
public class CarrinhoFinalizado {

    @Id
    private String id;
    private String usuarioId;
    private Set<Produto> produtos;
    @Setter
    private LocalDateTime dataDoPagamento;
    @Setter
    private Boolean statusPagamento;
    @Setter
    private Status statusDoPedido;

    // <>------ Construtores
    public CarrinhoFinalizado(String id, String usuarioId, Produto produto, LocalDateTime dataDoPagamento, Boolean statusPagamento, Status statusDoPedido) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.produtos = new HashSet<>();
        this.produtos.add(produto);
        this.dataDoPagamento = dataDoPagamento;
        this.statusPagamento = statusPagamento;
        this.statusDoPedido = statusDoPedido;
    }

    public CarrinhoFinalizado(CarrinhoAberto carrinhoAberto) {
        this.usuarioId = carrinhoAberto.getUsuarioId();
        this.produtos = carrinhoAberto.getProdutos();
    }
}

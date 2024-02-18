package br.com.fiap.techchallenge.carrinho.producers;

import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "estoque", url = "http://localhost:8081")
public interface EstoquePedidoProducer {

    @PostMapping(value = "/consumer-remover-estoque")
    void removerEstoque(Produtos produto);
}

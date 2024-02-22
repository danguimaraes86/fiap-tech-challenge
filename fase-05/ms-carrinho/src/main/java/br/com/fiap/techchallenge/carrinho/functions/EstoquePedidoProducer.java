package br.com.fiap.techchallenge.carrinho.functions;

import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "produtos", url = "http://localhost:3002")
public interface EstoquePedidoProducer {

    @PostMapping(value = "/removerestoque")
    void removerEstoque(Produtos produto);
}

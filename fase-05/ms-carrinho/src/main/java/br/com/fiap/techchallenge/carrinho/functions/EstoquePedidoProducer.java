package br.com.fiap.techchallenge.carrinho.functions;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "produtos", url = "http://localhost:3002")
public interface EstoquePedidoProducer {

    @PostMapping(value = "/consumer-removerestoque")
    void removerEstoque(Produtos produto);
}

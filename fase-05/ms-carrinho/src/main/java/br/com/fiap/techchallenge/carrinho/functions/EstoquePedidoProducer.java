package br.com.fiap.techchallenge.carrinho.functions;

import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "produtos", url = "http://localhost:3002")
public interface EstoquePedidoProducer {

//    @GetMapping(value = "/produtos/checarsetemestoque")
//    boolean checarSeHaEstoque(Produtos produto);
    @PostMapping(value = "/removerestoque")
    void removerEstoque(Produtos produto);
}

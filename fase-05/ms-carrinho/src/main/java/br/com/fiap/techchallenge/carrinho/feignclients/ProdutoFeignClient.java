package br.com.fiap.techchallenge.carrinho.feignclients;

import br.com.fiap.techchallenge.carrinho.entities.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(
        name = "ms-produtos",
        url = "localhost:3002",
        path = "/produtos"
)
public interface ProdutoFeignClient {
    @PostMapping("/checarsetemestoque")
    Boolean checarSeHaEstoque(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Produto produto);

    @PutMapping("/estoque/{id}")
    Produto updateEstoqueProduto(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @PathVariable("id") String id,
            @RequestParam Long alteracaoEstoque);
}

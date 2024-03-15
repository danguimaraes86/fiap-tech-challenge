package br.com.fiap.techchallenge.msoauth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(
        name = "ms-carrinho",
        url = "http://localhost:3003",
        path = "/carrinhos"
)
public interface CarrinhoFeignClient {

    @PostMapping("/novo")
    String createNovoCarrinho(@RequestBody String email);
}

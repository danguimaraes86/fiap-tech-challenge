package br.com.fiap.techchallenge.msoauth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(
        name = "ms-usuarios",
        url = "localhost:3001",
        path = "/usuarios"
)
public interface UsuarioFeignClient {
    @GetMapping("/{email}")
    ResponseEntity<UsuarioDTO> findUsuarioByEmail(@PathVariable String email);
}

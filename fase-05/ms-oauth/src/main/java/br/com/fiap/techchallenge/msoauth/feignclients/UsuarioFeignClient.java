package br.com.fiap.techchallenge.msoauth.feignclients;

import br.com.fiap.techchallenge.msoauth.feignclients.usuario.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Component
@FeignClient(
        name = "ms-usuarios",
        url = "localhost:3001",
        path = "/usuarios"
)
public interface UsuarioFeignClient {
    @GetMapping("/busca")
    Optional<Usuario> findUsuarioByEmail(@RequestParam String email);

    @PostMapping("/novo")
    Usuario createNovoUsuario(Usuario usuario);
}

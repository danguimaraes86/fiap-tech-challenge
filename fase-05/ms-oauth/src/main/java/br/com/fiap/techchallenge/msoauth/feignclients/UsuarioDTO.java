package br.com.fiap.techchallenge.msoauth.feignclients;

import br.com.fiap.techchallenge.msoauth.models.Role;

public record UsuarioDTO(
        String email,
        String nome,
        String password,
        Role role
) {
}

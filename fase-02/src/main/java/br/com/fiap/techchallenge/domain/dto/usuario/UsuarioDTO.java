package br.com.fiap.techchallenge.domain.dto.usuario;

import br.com.fiap.techchallenge.domain.entidade.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

public record UsuarioDTO(

        @Email
        @JsonProperty
        String email,
        @JsonProperty
        String senha
) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getEmail(), usuario.getSenha());
    }
}

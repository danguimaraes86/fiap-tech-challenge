package br.com.fiap.techchallenge.domain.dto.usuario;

import br.com.fiap.techchallenge.domain.entidade.Usuario;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
        public UsuarioDTO(Usuario usuario) {
                this(usuario.getEmail(), usuario.getSenha());
        }
}

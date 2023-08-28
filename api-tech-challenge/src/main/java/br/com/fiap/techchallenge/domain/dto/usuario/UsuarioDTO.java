package br.com.fiap.techchallenge.domain.dto.usuario;

import br.com.fiap.techchallenge.domain.entidade.Usuario;

public record UsuarioDTO(
        String email,
        String senha
) {
    public UsuarioDTO(Usuario usuario) {
        this(usuario.getEmail(), usuario.getSenha());
    }
}

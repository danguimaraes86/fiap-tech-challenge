package br.com.fiap.techchallenge.usuarios.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank String email,
        @NotBlank String nome,
        @NotBlank String password,
        String role
) {
}

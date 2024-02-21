package br.com.fiap.techchallenge.usuarios.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}

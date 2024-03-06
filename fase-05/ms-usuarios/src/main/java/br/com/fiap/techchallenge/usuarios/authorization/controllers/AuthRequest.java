package br.com.fiap.techchallenge.usuarios.authorization.controllers;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}

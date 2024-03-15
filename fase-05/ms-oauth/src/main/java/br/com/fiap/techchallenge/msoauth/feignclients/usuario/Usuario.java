package br.com.fiap.techchallenge.msoauth.feignclients.usuario;

import jakarta.validation.constraints.NotBlank;

public record Usuario(
        @NotBlank String email,
        @NotBlank String nome,
        @NotBlank String password,
        String role
) {
}

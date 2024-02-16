package br.com.fiap.techchallenge.usuarios.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank
//      [TODO] incluir @Email
        String email,
        @NotBlank
        String nome,
        @NotBlank @Min(value = 4)
        String password) {
}

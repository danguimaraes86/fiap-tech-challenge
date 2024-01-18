package br.com.fiap.techchallenge.domain.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank
        String nome,
        List<String> favoritos
) {
}

package br.com.fiap.techchallenge.domain;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record VideoDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        @NotBlank
        String url,
        LocalDateTime dataPublicacao,
        LocalDateTime ultimaAlteracao
) {
}

package br.com.fiap.techchallenge.domain.dtos;

import br.com.fiap.techchallenge.Categoria;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record VideoDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        @NotBlank
        String codeCategoria,
        @NotBlank
        String url,
        LocalDateTime dataPublicacao,
        LocalDateTime ultimaAlteracao
) {
}

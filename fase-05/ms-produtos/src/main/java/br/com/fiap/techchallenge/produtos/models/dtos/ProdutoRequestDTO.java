package br.com.fiap.techchallenge.produtos.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProdutoRequestDTO(
        @NotBlank String produtoId,
        @PositiveOrZero Long quantidade) {
}

package br.com.fiap.techchallenge.produtos.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

public record ProdutoDTO(
        String id,
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        Optional<@PositiveOrZero Double> preco,
        Optional<@PositiveOrZero Long> estoque) {
}

package br.com.fiap.techchallenge.produtos.model.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO(
        String id,
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @DecimalMin(value = "0.0")
        Double preco,
        Long estoque) {
}

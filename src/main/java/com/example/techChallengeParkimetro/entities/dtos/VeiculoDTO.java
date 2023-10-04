package com.example.techChallengeParkimetro.entities.dtos;

import com.example.techChallengeParkimetro.entities.Veiculo;
import jakarta.validation.constraints.NotBlank;

public record VeiculoDTO(
        String id,
        @NotBlank
        String marca,
        @NotBlank
        String modelo,
        @NotBlank
        String placa
) {
    public Veiculo toEntity() {
        return new Veiculo(marca, modelo, placa);
    }
}

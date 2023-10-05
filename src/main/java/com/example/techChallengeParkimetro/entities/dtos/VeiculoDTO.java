package com.example.techChallengeParkimetro.entities.dtos;

import com.example.techChallengeParkimetro.entities.Veiculo;
import jakarta.validation.constraints.NotBlank;

public record VeiculoDTO(
        // [TODO] Retirar id do retorno ao final do projeto. Apenas para facilitar consultas
        String id,
        @NotBlank
        String marca,
        @NotBlank
        String modelo,
        @NotBlank
        String placa,
        String condutorCpf
) {
    public Veiculo toEntity(String condutorCpf) {
        return new Veiculo(marca, modelo, placa, condutorCpf);
    }
}

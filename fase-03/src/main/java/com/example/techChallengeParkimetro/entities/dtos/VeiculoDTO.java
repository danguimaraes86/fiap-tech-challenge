package com.example.techChallengeParkimetro.entities.dtos;

import com.example.techChallengeParkimetro.entities.Veiculo;
import jakarta.validation.constraints.NotBlank;

public record VeiculoDTO(
        @NotBlank
        String marca,
        @NotBlank
        String modelo,
        @NotBlank
        String placa,
        String condutorCpf
) {
    public Veiculo toEntity(String condutorCpf) {
        return new Veiculo(marca, modelo, placa.toUpperCase(), condutorCpf);
    }
}

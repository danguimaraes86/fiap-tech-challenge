package br.com.fiap.techchallenge.entities.dtos;

import br.com.fiap.techchallenge.entities.Veiculo;
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

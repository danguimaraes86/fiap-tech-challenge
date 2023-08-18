package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ConsumidorDTO(

        @NotBlank @JsonProperty
        String usuarioId,
        @NotBlank @JsonProperty
        String nome,
        @NotBlank @JsonProperty
        String dataNascimento,
        @NotBlank @JsonProperty
        String sexo,
        @NotBlank @JsonProperty
        String parentesco
) {

    public ConsumidorDTO(Consumidor consumidor) {
        this(String.valueOf(consumidor.getUsuario().getId()) ,consumidor.getNome(), consumidor.getDataNascimento().toString(),
                consumidor.getSexo(), consumidor.getParentesco());
    }
}

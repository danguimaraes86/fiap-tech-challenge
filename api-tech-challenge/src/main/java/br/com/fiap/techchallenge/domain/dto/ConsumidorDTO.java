package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record ConsumidorDTO(

        @NotBlank @JsonProperty
        String usuarioId,
        @NotBlank @JsonProperty
        String nome,
        @NotBlank @JsonProperty
        String dataNascimento,
        @NotBlank @JsonProperty
        String sexo,
        Set<Eletrodomestico> eletrodomesticos,
        @NotBlank @JsonProperty
        String parentesco
) {

    public ConsumidorDTO(Consumidor consumidor) {
        this(String.valueOf(consumidor.getUsuario().getId()), consumidor.getNome(), consumidor.getDataNascimento().toString(),
                consumidor.getSexo(), consumidor.getEletrodomesticos(), consumidor.getParentesco());
    }
}
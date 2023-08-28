package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record ConsumidorDTO(

        @JsonProperty
        String usuarioId,
        @JsonProperty
        String nome,
        @JsonProperty
        String dataNascimento,
        @JsonProperty
        String sexo,
        Set<Eletrodomestico> eletrodomesticos,
        @JsonProperty
        String parentesco
) {

    public ConsumidorDTO(Consumidor consumidor) {
        this(String.valueOf(consumidor.getUsuario().getId()), consumidor.getNome(), consumidor.getDataNascimento().toString(),
                consumidor.getSexo(), consumidor.getEletrodomesticos(), consumidor.getParentesco());
    }
}
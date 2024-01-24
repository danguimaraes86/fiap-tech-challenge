package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record EletrodomesticoDTO(
        @JsonProperty
        String nome,
        @JsonProperty
        String potencia,
        @JsonProperty
        String modelo,
        @JsonProperty
        String fabricacao,
        @JsonProperty
        Long usuarioId,
        @JsonProperty
        Long enderecoId,
        @JsonProperty
        Set<Long> consumidoresIds
) {

    public EletrodomesticoDTO(Eletrodomestico eletrodomesticos) {
        this(eletrodomesticos.getNome(),
                eletrodomesticos.getPotencia(),
                eletrodomesticos.getModelo(),
                eletrodomesticos.getFabricacao().toString(),
                eletrodomesticos.getUsuario().getId(),
                eletrodomesticos.getEndereco().getId(),
                (eletrodomesticos.getConsumidores() != null)
                        ? eletrodomesticos.getConsumidores().stream()
                        .map(consumidor -> consumidor.getId())
                        .collect(Collectors.toSet()) : new HashSet<>());
    }
}

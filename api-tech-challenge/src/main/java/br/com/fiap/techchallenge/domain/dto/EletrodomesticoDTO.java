package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

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
        String usuarioId,
        Endereco endereco,
        Set<Consumidor> consumidores
) {

    public EletrodomesticoDTO(Eletrodomestico eletrodomesticos) {
        this(eletrodomesticos.getNome(),
                eletrodomesticos.getPotencia(), eletrodomesticos.getModelo(),
                eletrodomesticos.getFabricacao().toString(),
                eletrodomesticos.getUsuario().getId().toString(),
                eletrodomesticos.getEndereco(), eletrodomesticos.getConsumidores());
    }
}

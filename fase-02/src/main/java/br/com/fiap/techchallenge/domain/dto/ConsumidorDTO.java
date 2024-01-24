package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ConsumidorDTO(

        @JsonProperty
        String nome,
        @JsonProperty
        String dataNascimento,
        @JsonProperty
        String sexo,
        @JsonProperty
        String parentesco,
        @JsonProperty
        Set<Long> eletrodomesticosIds,
        @JsonProperty
        Long usuarioId

) {

    public ConsumidorDTO(Consumidor consumidor) {
        this(consumidor.getNome(),
                consumidor.getDataNascimento().toString(),
                consumidor.getSexo(),
                consumidor.getParentesco(),

                (consumidor.getEletrodomesticos() != null)
                    ? consumidor.getEletrodomesticos().stream()
                        .map(eletrodomestico -> eletrodomestico.getId())
                                .collect(Collectors.toSet()) : new HashSet<>(),
                consumidor.getUsuario().getId());
    }
}
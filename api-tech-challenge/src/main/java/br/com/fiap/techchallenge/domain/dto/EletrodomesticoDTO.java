package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Endereco;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public record EletrodomesticoDTO(
        @NotBlank @JsonProperty
        String nome,
        @NotBlank @JsonProperty
        String potencia,
        @NotBlank @JsonProperty
        String modelo,
        @NotBlank @JsonProperty
        String fabricacao,
        @NotNull @JsonProperty
        String usuarioId,
        Endereco endereco,
        Set<Consumidor> consumidores
) {

    public EletrodomesticoDTO(Eletrodomestico eletrodomesticos) {
        this(eletrodomesticos.getNome(), eletrodomesticos.getPotencia(), eletrodomesticos.getModelo(), eletrodomesticos.getFabricacao().toString(),
                String.valueOf(eletrodomesticos.getUsuario().getId()), eletrodomesticos.getEndereco(), eletrodomesticos.getConsumidores());
    }
}

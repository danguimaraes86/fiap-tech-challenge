package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

public record EletrodomesticoDTO(
        @Getter @NotBlank @JsonProperty
        String nome,
        @Getter @NotBlank @JsonProperty
        String potencia,
        @Getter @NotBlank @JsonProperty
        String modelo,
        @Getter @NotBlank @JsonProperty
        String fabricacao
) {

    public EletrodomesticoDTO(Eletrodomestico eletrodomesticos) {
        this(eletrodomesticos.getNome(), eletrodomesticos.getPotencia(), eletrodomesticos.getModelo(), eletrodomesticos.getFabricacao().toString());
    }

    public Eletrodomestico toEletrodomestico() {
        LocalDate fabricacao = LocalDate.parse(this.fabricacao);
        return new Eletrodomestico(this.nome, this.potencia, this.modelo, fabricacao);
    }
}

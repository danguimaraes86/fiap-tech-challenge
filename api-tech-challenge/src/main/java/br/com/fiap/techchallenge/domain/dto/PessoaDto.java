package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

public record PessoaDto(
        @Getter @NotBlank @JsonProperty
        String nome,
        @Getter @NotBlank @JsonProperty
        String dataNascimento,
        @Getter @NotBlank @JsonProperty
        String sexo,
        @Getter @NotBlank @JsonProperty
        String parentesco
) {

    public PessoaDto(Consumidor consumidor) {
        this(consumidor.getNome(), consumidor.getDataNascimento().toString(), consumidor.getSexo(), consumidor.getParentesco());
    }

    public Consumidor toPessoa() {
        LocalDate dataNascimento = LocalDate.parse(this.dataNascimento);
        return new Consumidor(this.nome, dataNascimento, this.sexo, this.parentesco);
    }
}

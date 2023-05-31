package br.com.fiap.techchallenge.usuarios.apiusuarios.domain.dto;

import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.entidade.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;


public record PessoaDto(
        @NotBlank @JsonProperty
        String nome,
        @NotBlank @JsonProperty
        String dataNascimento,
        @NotBlank @JsonProperty
        String sexo,
        @NotBlank @JsonProperty
        String parentesco
) {

    public PessoaDto(Pessoa pessoa) {
        this(pessoa.getNome(), pessoa.getDataNascimento().toString(), pessoa.getSexo(), pessoa.getParentesco());
    }

    public Pessoa toPessoa(){
        LocalDateTime dataNascimento = LocalDateTime.parse(this.dataNascimento);
        return new Pessoa(this.nome, dataNascimento, this.sexo, this.parentesco);
    }

}

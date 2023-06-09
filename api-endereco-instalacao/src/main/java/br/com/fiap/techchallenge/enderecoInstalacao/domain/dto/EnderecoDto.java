package br.com.fiap.techchallenge.enderecoInstalacao.domain.dto;

import br.com.fiap.techchallenge.enderecoInstalacao.domain.entidade.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record EnderecoDto (
        @Getter @NotBlank @JsonProperty
        String nomeInstalacao,
        @Getter @NotBlank @JsonProperty
        String rua,
        @Getter @NotBlank @JsonProperty
        String numero,
        @Getter @NotBlank @JsonProperty
        String complemento

){
    public EnderecoDto(Endereco endereco){
        this(endereco.getNomeInstalacao(), endereco.getRua(), endereco.getNumero(), endereco.getComplemento());
    }
    public Endereco toEndereco(){
        return new Endereco(this.nomeInstalacao, this.rua, this.numero, this.complemento);
    }
}

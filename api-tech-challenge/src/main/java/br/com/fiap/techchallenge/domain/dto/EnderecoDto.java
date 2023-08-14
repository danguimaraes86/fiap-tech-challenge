package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record EnderecoDto(
        @Getter @NotBlank @JsonProperty
        String nomeInstalacao,
        @Getter @NotBlank @JsonProperty
        String rua,
        @Getter @NotBlank @JsonProperty
        String numero,
        @Getter @NotBlank @JsonProperty
        String bairro,
        @Getter @NotBlank @JsonProperty
        String cidade,
        @Getter @NotBlank @JsonProperty
        String estado
) {

    public EnderecoDto(Endereco endereco) {
        this(endereco.getNomeInstalacao(), endereco.getRua(), endereco.getNumero(), endereco.getBairro(),
                endereco.getCidade(), endereco.getEstado());
    }

    public Endereco toEndereco() {
        return new Endereco(this.nomeInstalacao, this.rua, this.numero, this.bairro, this.cidade, this.estado);
    }
}

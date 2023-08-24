package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        @NotBlank @JsonProperty
        String nomeInstalacao,
        @NotBlank @JsonProperty
        String rua,
        @NotBlank @JsonProperty
        String numero,
        @NotBlank @JsonProperty
        String bairro,
        @NotBlank @JsonProperty
        String cidade,
        @NotBlank @JsonProperty
        String estado,
        @NotBlank@JsonProperty
        String usuarioId
) {

    public EnderecoDTO(Endereco endereco) {
        this(endereco.getNomeInstalacao(), endereco.getRua(), endereco.getNumero(), endereco.getBairro(),
                endereco.getCidade(), endereco.getEstado(), String.valueOf(endereco.getUsuario().getId()));
    }

    public Endereco toEndereco() {
        return new Endereco(this.nomeInstalacao, this.rua, this.numero, this.bairro, this.cidade, this.estado);
    }
}

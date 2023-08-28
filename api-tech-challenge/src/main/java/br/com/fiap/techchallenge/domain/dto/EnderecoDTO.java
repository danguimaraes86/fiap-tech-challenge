package br.com.fiap.techchallenge.domain.dto;

import br.com.fiap.techchallenge.domain.entidade.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EnderecoDTO(
        @JsonProperty
        String nomeInstalacao,
        @JsonProperty
        String rua,
        @JsonProperty
        String numero,
        @JsonProperty
        String bairro,
        @JsonProperty
        String cidade,
        @JsonProperty
        String estado,
        @JsonProperty
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

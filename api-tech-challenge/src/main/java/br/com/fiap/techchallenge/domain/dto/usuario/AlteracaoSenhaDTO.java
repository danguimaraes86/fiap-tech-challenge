package br.com.fiap.techchallenge.domain.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record AlteracaoSenhaDTO(
        @NotBlank
        String senha
) {
}

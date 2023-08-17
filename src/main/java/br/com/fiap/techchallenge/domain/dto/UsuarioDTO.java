package br.com.fiap.techchallenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record UsuarioDTO(
        @Getter @NotBlank @JsonProperty
        String email
) {
}

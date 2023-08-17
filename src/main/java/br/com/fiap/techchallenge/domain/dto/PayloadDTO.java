package br.com.fiap.techchallenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public record PayloadDTO<T>(
        @Getter @NotBlank @JsonProperty
        T data,
        @Getter @NotBlank @JsonProperty
        UsuarioDTO usuario
) {
}

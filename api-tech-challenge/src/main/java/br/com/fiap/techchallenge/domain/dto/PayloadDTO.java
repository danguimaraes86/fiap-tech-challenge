package br.com.fiap.techchallenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PayloadDTO<T>(
        @NotBlank @JsonProperty
        T data,
        @NotBlank @JsonProperty
        String usuario
) {
}

package br.com.fiap.techchallenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PayloadDTO<T>(
        @JsonProperty
        T data,
        @JsonProperty
        String usuario
) {
}

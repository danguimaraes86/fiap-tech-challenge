package br.com.fiap.techchallenge.produtos.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public record DefaultError(
        LocalDateTime timestamp,
        HttpStatus status,
        String path,
        Map<String, String> errors
) {
}

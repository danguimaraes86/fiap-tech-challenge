package br.com.fiap.techchallenge.domain;

import java.time.LocalDateTime;

public record VideoDTO(
        String titulo,
        String descricao,
        String url,
        LocalDateTime dataPublicacao,
        LocalDateTime ultimaAlteracao
) {

    public Video toEntity() {
        return new Video(titulo, descricao, url, dataPublicacao);
    }
}

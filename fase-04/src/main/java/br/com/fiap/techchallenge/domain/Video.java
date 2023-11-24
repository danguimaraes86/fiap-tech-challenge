package br.com.fiap.techchallenge.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Document
public class Video {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String url;
    private final LocalDateTime dataPublicacao = LocalDateTime.now();

    public Video(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }
}

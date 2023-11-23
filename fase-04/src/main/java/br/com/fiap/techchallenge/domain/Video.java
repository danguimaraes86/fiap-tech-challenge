package br.com.fiap.techchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class Video {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String url;
    private LocalDateTime dataPublicacao;
}

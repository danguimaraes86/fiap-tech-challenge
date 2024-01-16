package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document
@Setter
public class Video {
    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String url;
    private LocalDateTime dataPublicacao;
    private LocalDateTime ultimaAlteracao;

    public Video(String titulo, String descricao, String url, LocalDateTime dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.dataPublicacao = dataPublicacao;
        this.ultimaAlteracao = dataPublicacao;
    }

    public Video update(VideoDTO videoDTO) {
        if (videoDTO.titulo() != null)
            this.titulo = videoDTO.titulo();
        if (videoDTO.descricao() != null)
            this.descricao = videoDTO.descricao();
        if (videoDTO.url() != null)
            this.url = videoDTO.url();
        this.ultimaAlteracao = LocalDateTime.now();
        return this;
    }

    public VideoDTO toVideoDTO() {
        return new VideoDTO(this.titulo, this.descricao, this.url, this.dataPublicacao, this.ultimaAlteracao);
    }
}

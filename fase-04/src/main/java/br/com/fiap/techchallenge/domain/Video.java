package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import br.com.fiap.techchallenge.domain.enums.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document
public class Video {
    @Id
    private ObjectId id;
    private String titulo;
    private String descricao;
    private String categoria;
    private String url;
    private Integer visualizacoes = 0;
    private LocalDateTime dataPublicacao;
    private LocalDateTime ultimaAlteracao;

    public Video(String titulo, String descricao, String codeCategoria, String url, LocalDateTime dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = Categoria.getEnum(codeCategoria).getGenero();
        this.url = url;
        this.dataPublicacao = dataPublicacao;
        this.ultimaAlteracao = dataPublicacao;
    }

    public Video update(VideoDTO videoDTO) {
        if (videoDTO.titulo() != null)
            this.titulo = videoDTO.titulo();
        if (videoDTO.descricao() != null)
            this.descricao = videoDTO.descricao();
        if (videoDTO.codeCategoria() != null)
            this.categoria = Categoria.getEnum(videoDTO.codeCategoria()).getGenero();
        if (videoDTO.url() != null)
            this.url = videoDTO.url();
        this.ultimaAlteracao = LocalDateTime.now();
        return this;
    }

    public Video addVisualizacao() {
        this.visualizacoes++;
        return this;
    }

    public VideoDTO toVideoDTO() {
        return new VideoDTO(this.titulo, this.descricao, this.categoria, this.url, this.dataPublicacao, this.ultimaAlteracao);
    }
}

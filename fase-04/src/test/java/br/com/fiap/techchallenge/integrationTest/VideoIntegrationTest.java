package br.com.fiap.techchallenge.integrationTest;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import br.com.fiap.techchallenge.services.VideoService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.fiap.techchallenge.utils.VideoUtil.gerarVideoDTOMock;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("integration")
class VideoIntegrationTest {

    @Autowired
    private VideoService service;

    @Autowired
    private VideoRepository repository;

    @Test
    void deveListarTodosOsVideos() {
        var listaVideos = repository.findAll();
        assertThat(listaVideos).hasSizeGreaterThan(0);
    }

    @Test
    void deveResgistrarUmVideo() {
        VideoDTO video = gerarVideoDTOMock();
        var videoRegistrado = service.insert(video);

        assertThat(videoRegistrado).isInstanceOf(Video.class).isNotNull();
        assertThat(videoRegistrado.getUrl()).isEqualTo(video.url());
        assertThat(videoRegistrado.getTitulo()).isEqualTo(video.titulo());
    }

    @Test
    void deveBuscarPorUmVideo() {
        VideoDTO videoMock = gerarVideoDTOMock();
        Video video = service.insert(videoMock);

        var videoEncontrado = service.findById(video.getId());
        assertThat(videoEncontrado).isNotNull();
        assertThat(videoEncontrado.getId()).isEqualTo(video.getId());
    }

    @Test
    void deveRemoverUmVideo() {
        VideoDTO videoDTOMock = gerarVideoDTOMock();
        Video video = service.insert(videoDTOMock);
        service.deleteById(video.getId());

        var videoDeletado = repository.findById(video.getId());
        assertThat(videoDeletado).isEmpty();
    }

    @Test
    void deveAtualizarUmVideo() {
        VideoDTO videoOriginal = gerarVideoDTOMock();
        Video video = service.insert(videoOriginal);

        VideoDTO videoDTONovo = gerarVideoDTOMock();
        Video videoAlterado = service.updateVideoById(video.getId(), videoDTONovo);

        assertThat(video.getId()).isEqualTo(videoAlterado.getId());
        assertThat(video.getTitulo()).isNotEqualTo(videoAlterado.getTitulo());
    }
}

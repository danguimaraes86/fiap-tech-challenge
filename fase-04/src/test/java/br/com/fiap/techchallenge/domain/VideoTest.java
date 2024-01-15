package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.utils.VideoUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoTest {

    @Test
    void deveCriarObjetoCorretamente() {
        Video video = new Video();
        assertThat(video)
                .isInstanceOf(Video.class);
    }

    @Test
    void deveAtualizarDadosVideo() {
        Video videoFake = VideoUtil.gerarVideoMock();
        VideoDTO videoDTOFake = VideoUtil.gerarVideoDTOMock();

        Video videoAtualizado = videoFake.update(videoDTOFake);

        assertThat(videoFake).isEqualTo(videoAtualizado);
        assertThat(videoAtualizado.getId()).isNotEmpty().isNotBlank();
        assertThat(videoAtualizado.getTitulo()).isEqualTo(videoDTOFake.titulo());
        assertThat(videoAtualizado.getUrl()).isEqualTo(videoDTOFake.url());
        assertThat(videoAtualizado.getDescricao()).isEqualTo(videoDTOFake.descricao());
        assertThat(videoAtualizado.getDataPublicacao()).isBefore(videoFake.getUltimaAlteracao());
    }

    @Test
    void deveRetornarVideoDTO() {
        Video videoFake = VideoUtil.gerarVideoMock();
        VideoDTO videoDTO = videoFake.toVideoDTO();

        assertThat(videoDTO.titulo()).isEqualTo(videoFake.getTitulo());
        assertThat(videoDTO.descricao()).isEqualTo(videoFake.getDescricao());
        assertThat(videoDTO.url()).isEqualTo(videoFake.getUrl());
        assertThat(videoDTO.dataPublicacao()).isEqualTo(videoFake.getDataPublicacao());
        assertThat(videoDTO.ultimaAlteracao()).isEqualTo(videoFake.getUltimaAlteracao());
    }
}

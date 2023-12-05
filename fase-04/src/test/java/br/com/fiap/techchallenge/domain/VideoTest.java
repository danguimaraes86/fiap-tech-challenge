package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.utils.VideoUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoTest {

    @Test
    void deveAtualizarDadosVideo() {
        Video videoFake = VideoUtil.gerarVideoMock();
        VideoDTO videoDTOFake = VideoUtil.gerarVideoDTOMock();

        videoFake.update(videoDTOFake);

        assertThat(videoFake).isNotEqualTo(videoDTOFake.toEntity());
        assertThat(videoFake.getId()).isNotEmpty().isNotBlank();
        assertThat(videoFake.getTitulo()).isEqualTo(videoDTOFake.titulo());
        assertThat(videoFake.getUrl()).isEqualTo(videoDTOFake.url());
        assertThat(videoFake.getDescricao()).isEqualTo(videoDTOFake.descricao());
        assertThat(videoFake.getDataPublicacao()).isBefore(videoFake.getUltimaAlteracao());
    }
}

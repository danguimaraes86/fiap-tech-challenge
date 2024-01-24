package br.com.fiap.techchallenge.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VideoTest {

    @Test
    void deveCriarObjetoCorretamente() {
        Video video = new Video();
        assertThat(video)
                .isInstanceOf(Video.class);
    }
}

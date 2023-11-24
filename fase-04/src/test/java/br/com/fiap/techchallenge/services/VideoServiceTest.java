package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import br.com.fiap.techchallenge.utils.VideoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VideoServiceTest {

    private AutoCloseable mocks;
    private VideoService videoService;
    @Mock
    private VideoRepository videoRepository;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        videoService = new VideoService(videoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Nested
    class BuscarVideos {

        @Test
        void deveListarTodosOsVideos() {
            List<Video> videoFaker = Arrays.asList(
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock()
            );
            when(videoRepository.findAll()).thenReturn(videoFaker);

            List<Video> videoList = videoService.findAll();
            verify(videoRepository, times(1)).findAll();
            assertThat(videoList)
                    .hasSize(3)
                    .isEqualTo(videoFaker);
        }
    }
}

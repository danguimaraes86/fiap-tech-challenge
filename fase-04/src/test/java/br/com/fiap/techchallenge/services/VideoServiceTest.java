package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import br.com.fiap.techchallenge.utils.VideoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.ranges.Range;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                    .isEqualTo(videoFaker)
                    .contains(videoFaker.get(Range.START_TO_START))
                    .doesNotContainNull()
                    .doesNotContain(VideoUtil.gerarVideoMock());
        }

        @Test
        void deveBuscarVideoPorId() {
            Video videoFaker = VideoUtil.gerarVideoMock();
            String id = videoFaker.getId();
            when(videoRepository.findById(id)).thenReturn(Optional.of(videoFaker));

            Video video = videoService.findById(id);
            verify(videoRepository, times(1)).findById(anyString());
            assertThat(video)
                    .isInstanceOf(Video.class)
                    .isEqualTo(videoFaker)
                    .isNotNull();
            assertThat(video.getId()).isEqualTo(id);
        }

        @Test
        void deveLancarExcecao_BuscarVideoPorId_VideoNaoEncontrado() {
            Video videoFaker = VideoUtil.gerarVideoMock();
            String id = videoFaker.getId();
            when(videoRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> videoService.findById(id))
                    .isInstanceOf(VideoNotFoundException.class)
                    .hasMessage("video não encontrado");
        }
    }

    @Nested
    class InserirVideos {

        @Test
        void deveInserirVideo_RetornaVideoInserido() {
            Video videoFake = VideoUtil.gerarVideoMock();
            VideoDTO videoDTOfake = VideoUtil.gerarVideoDTOMock();
            when(videoRepository.insert(any(Video.class))).thenReturn(videoFake);

            Video videoNovo = videoService.insert(videoDTOfake);
            verify(videoRepository, times(1)).insert(any(Video.class));
            assertThat(videoNovo).isInstanceOf(Video.class)
                    .isEqualTo(videoFake);
            assertThat(videoNovo.getId()).isEqualTo(videoFake.getId());
        }
    }
}

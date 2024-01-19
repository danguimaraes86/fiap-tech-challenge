package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import br.com.fiap.techchallenge.utils.VideoUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.w3c.dom.ranges.Range;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
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
            Page<Video> videoFakerPage = new PageImpl<>(Arrays.asList(
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock()
            ));
            when(videoRepository.findAll(any(Pageable.class))).thenReturn(videoFakerPage);

            Page<Video> videoPage = videoService.findAll(Pageable.unpaged());
            assertThat(videoPage.getContent())
                    .hasSize(3)
                    .contains(videoFakerPage.getContent().get(Range.START_TO_START))
                    .doesNotContainNull()
                    .doesNotContain(VideoUtil.gerarVideoMock())
                    .isEqualTo(videoFakerPage.getContent());
            verify(videoRepository, times(1)).findAll(any(Pageable.class));
        }

        @Test
        void deveRetornarListaVazia() {
            Page<Video> videoFakerPage = new PageImpl<>(Collections.emptyList());
            when(videoRepository.findAll(any(Pageable.class))).thenReturn(videoFakerPage);

            Page<Video> videoPage = videoService.findAll(Pageable.unpaged());
            assertThat(videoPage.getContent())
                    .isEmpty();
            verify(videoRepository, times(1)).findAll(any(Pageable.class));
        }

        @Test
        void deveBuscarVideoPorId() {
            Video videoFaker = VideoUtil.gerarVideoMock();
            ObjectId id = videoFaker.getId();
            when(videoRepository.findById(id)).thenReturn(Optional.of(videoFaker));

            Video video = videoService.findById(id);
            verify(videoRepository, times(1)).findById(id);
            assertThat(video)
                    .isInstanceOf(Video.class)
                    .isEqualTo(videoFaker)
                    .isNotNull();
            assertThat(video.getId()).isEqualTo(id);
        }

        @Test
        void deveBuscarVideoPorAtributo() {
            Page<Video> videoFakerPage = new PageImpl<>(Arrays.asList(
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock()
            ));
            Video videoFake = videoFakerPage.getContent().get(0);
            when(videoRepository.findVideoByTituloLikeIgnoreCaseAndDataPublicacaoBefore(
                    anyString(), any(LocalDateTime.class), any(Pageable.class)))
                    .thenReturn(videoFakerPage);

            Page<Video> videoPage = videoService
                    .findByAtributo(videoFake.getTitulo(), videoFake.getDataPublicacao(), Pageable.unpaged());
            verify(videoRepository, times(1))
                    .findVideoByTituloLikeIgnoreCaseAndDataPublicacaoBefore(
                            anyString(), any(LocalDateTime.class), any(Pageable.class));
            assertThat(videoPage.getContent())
                    .isEqualTo(videoFakerPage.getContent());
            assertThat(videoPage.getContent().get(0))
                    .isEqualTo(videoFake);
            assertThat(videoPage.getContent().get(1))
                    .isNotEqualTo(videoFake);
        }

        @Test
        void deveLancarExcecao_BuscarVideoPorId_VideoNaoEncontrado() {
            Video videoFaker = VideoUtil.gerarVideoMock();
            ObjectId id = videoFaker.getId();
            when(videoRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> videoService.findById(id))
                    .isInstanceOf(VideoNotFoundException.class)
                    .hasMessage(String.format("video_id %s não encontrado", id));
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
            assertThat(videoNovo.getUltimaAlteracao()).isAfterOrEqualTo(videoFake.getDataPublicacao());
        }
    }

    @Nested
    class RemoverVideos {

        @Test
        void deveRemoverVideoPorId() {
            Video videoFake = VideoUtil.gerarVideoMock();
            ObjectId id = videoFake.getId();
            when(videoRepository.findById(id)).thenReturn(Optional.of(videoFake));

            videoService.deleteById(id);
            verify(videoRepository, times(1)).findById(id);
            verify(videoRepository, times(1)).delete(videoFake);
        }

        @Test
        void deveLancarExcecao_RemoverVideoPorId_VideoNaoEncontrado() {
            Video videoFaker = VideoUtil.gerarVideoMock();
            ObjectId id = videoFaker.getId();
            when(videoRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> videoService.deleteById(id))
                    .isInstanceOf(VideoNotFoundException.class)
                    .hasMessage(String.format("video_id %s não encontrado", id));
        }
    }

    @Nested
    class AlterarVideos {

        @Test
        void deveAlterarVideoPorId() {
            Video videoFake = VideoUtil.gerarVideoMock();
            ObjectId id = videoFake.getId();
            VideoDTO videoDTO = VideoUtil.gerarVideoDTOMock();
            when(videoRepository.findById(id)).thenReturn(Optional.of(videoFake));
            videoFake.update(videoDTO);
            when(videoRepository.save(any(Video.class))).thenReturn(videoFake);

            Video videoAtualizado = videoService.updateVideoById(id, videoDTO);
            verify(videoRepository, times(1)).findById(id);
            verify(videoRepository, times(1)).save(videoAtualizado);

            assertThat(videoAtualizado).isNotNull().isEqualTo(videoFake);
            assertThat(videoAtualizado.getId()).isEqualTo(videoFake.getId());
            assertThat(videoAtualizado.getTitulo()).isEqualTo(videoFake.getTitulo());
            assertThat(videoAtualizado.getUltimaAlteracao()).isAfter(videoAtualizado.getDataPublicacao());
        }

        @Test
        void deveLancarExcecao_AlterarVideoPorId_VideoNaoEncontrado() {
            Video videoFaker = VideoUtil.gerarVideoMock();
            ObjectId id = videoFaker.getId();
            VideoDTO videoDTO = VideoUtil.gerarVideoDTOMock();
            when(videoRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> videoService.updateVideoById(id, videoDTO))
                    .isInstanceOf(VideoNotFoundException.class)
                    .hasMessage(String.format("video_id %s não encontrado", id));
        }
    }
}

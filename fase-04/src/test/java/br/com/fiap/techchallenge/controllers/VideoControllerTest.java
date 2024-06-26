package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import br.com.fiap.techchallenge.domain.enums.Categoria;
import br.com.fiap.techchallenge.exceptions.ControllerExceptionHandler;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.services.VideoService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static br.com.fiap.techchallenge.utils.JsonUtil.toJsonString;
import static br.com.fiap.techchallenge.utils.VideoUtil.gerarVideoDTOMock;
import static br.com.fiap.techchallenge.utils.VideoUtil.gerarVideoMock;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VideoControllerTest {

    private AutoCloseable mock;
    private MockMvc mockMvc;
    @InjectMocks
    private VideoController videoController;
    @Mock
    private VideoService videoService;

    @BeforeEach
    void setupUp() {
        mock = MockitoAnnotations.openMocks(this);
        VideoController videoController = new VideoController(videoService);
        mockMvc = MockMvcBuilders.standaloneSetup(videoController)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarVideos {

        @Test
        void deveRetornarListaVazia() throws Exception {
            Page<Video> videoFakerList = new PageImpl<>(Collections.emptyList());
            when(videoService.findAll(any(Pageable.class)))
                    .thenReturn(videoFakerList);

            mockMvc.perform(get("/videos"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoFakerList)));
            verify(videoService, times(1))
                    .findAll(any(Pageable.class));
        }

        @Test
        void deveListarTodosOsVideos() throws Exception {
            Page<Video> videoFakerList = new PageImpl<>(Arrays.asList(
                    gerarVideoMock(),
                    gerarVideoMock(),
                    gerarVideoMock()
            ));
            when(videoService.findAll(any(Pageable.class)))
                    .thenReturn(videoFakerList);

            Page<VideoDTO> videoDTOList = videoFakerList.map(Video::toVideoDTO);
            mockMvc.perform(get("/videos"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoDTOList)));
            verify(videoService, times(1))
                    .findAll(any(Pageable.class));
        }

        @Test
        void deveBuscarVideoPorId() throws Exception {
            Video videoFake = gerarVideoMock();
            ObjectId id = videoFake.getId();
            when(videoService.findById(any(ObjectId.class)))
                    .thenReturn(videoFake);

            VideoDTO videoDTO = videoFake.toVideoDTO();
            mockMvc.perform(get("/videos/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoDTO)));
            verify(videoService, times(1))
                    .findById(id);
        }

        @Test
        void deveBuscarVideoPorCategoria() throws Exception {
            List<Video> videoFakeList = Collections.singletonList(gerarVideoMock());
            String categoria = Objects.requireNonNull(Categoria.getEnum("1")).getCode();
            when(videoService.findByCategoria(categoria))
                    .thenReturn(videoFakeList);

            Page<VideoDTO> videoDTOPage = new PageImpl<>(videoFakeList.stream().map(Video::toVideoDTO).toList());
            mockMvc.perform(get("/videos/categoria/{codeCategoria}", categoria)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoDTOPage)));
        }

        @Test
        void deveBuscarVideoPorAtributo() throws Exception {
            Page<Video> videoFakerList = new PageImpl<>(Arrays.asList(
                    gerarVideoMock(),
                    gerarVideoMock(),
                    gerarVideoMock()
            ));
            Video videoFake = videoFakerList.getContent().stream().findFirst().orElseThrow();

            when(videoService.findByAtributo(anyString(), any(LocalDateTime.class), any(Pageable.class)))
                    .thenReturn(videoFakerList);

            Page<VideoDTO> videoDTOPage = videoFakerList.map(Video::toVideoDTO);
            mockMvc.perform(get("/videos/busca", Pageable.unpaged())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("titulo", videoFake.getTitulo()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoDTOPage)));
            verify(videoService, times(1))
                    .findByAtributo(anyString(), any(LocalDateTime.class), any(Pageable.class));
        }
    }

    @Nested
    class InserirVideo {

        @Test
        void deveInserirVideo() throws Exception {
            Video videoFake = gerarVideoMock();
            VideoDTO videoDTOFake = videoFake.toVideoDTO();
            when(videoService.insert(videoDTOFake))
                    .thenReturn(videoFake);

            mockMvc.perform(post("/videos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(videoDTOFake)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(toJsonString(videoDTOFake)))
                    .andExpect(header().string("Location", containsString(videoFake.getId().toHexString())));
            verify(videoService, times(1))
                    .insert(any(VideoDTO.class));
        }
    }

    @Nested
    class AlterarVideo {

        @Test
        void deveAtualizarVideo() throws Exception {
            Video videoInicial = gerarVideoMock();
            VideoDTO videoForm = gerarVideoDTOMock();
            ObjectId id = videoInicial.getId();

            Video videoAtualizado = videoInicial.update(videoForm);
            when(videoService.updateVideoById(id, videoForm))
                    .thenReturn(videoAtualizado);

            mockMvc.perform(put("/videos/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(videoForm)))
                    .andExpect(status().isAccepted())
                    .andExpect(content().json(toJsonString(videoAtualizado.toVideoDTO())))
                    .andExpect(jsonPath("$.titulo").value(videoForm.titulo()))
                    .andExpect(jsonPath("$.descricao").value(videoForm.descricao()));
            verify(videoService, times(1))
                    .updateVideoById(id, videoForm);
        }
    }

    @Nested
    class RemoverVideo {

        @Test
        void deveRemoverVideo() throws Exception {
            ObjectId id = ObjectId.get();

            mockMvc.perform(delete("/videos/{id}", id))
                    .andExpect(status().isNoContent());
            verify(videoService, times(1))
                    .deleteById(id);

        }
    }

    @Nested
    class Watch {

        @Test
        void deveRetornar_MonoVideo() {
            Video videoMock = gerarVideoMock();
            when(videoService.watchVideo(any(ObjectId.class))).thenReturn(Mono.just(videoMock));

            StepVerifier.create(videoController.watchVideo(videoMock.getId()))
                    .expectSubscription()
                    .expectNextMatches(responseEntity -> {
                        VideoDTO videoDTO = responseEntity.getBody();
                        HttpStatusCode httpStatus = responseEntity.getStatusCode();
                        return videoDTO != null &&
                                videoDTO.equals(videoMock.toVideoDTO()) &&
                                httpStatus.is2xxSuccessful();
                    })
                    .verifyComplete();
        }
    }

    @Nested
    class Exceptions {

        @Test
        void deveLancarExcecao_BuscarVideoPorId_VideoNaoEncontrado() throws Exception {
            ObjectId id = ObjectId.get();
            when(videoService.findById(any(ObjectId.class)))
                    .thenThrow(VideoNotFoundException.class);

            mockMvc.perform(get("/videos/{id}", id))
                    .andExpect(status().isNotFound());
            verify(videoService, times(1))
                    .findById(id);
        }

        @Test
        void deveLancarExcecao_InserirVideo_dadosInvalidos() throws Exception {
            VideoDTO videoDTOFake = new VideoDTO(null, null, null, null, null, null);

            mockMvc.perform(post("/videos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(videoDTOFake)))
                    .andExpect(status().isBadRequest());
            verify(videoService, never())
                    .insert(any(VideoDTO.class));
        }

        @Test
        void deveLancarExcecao_AlterarVideo_IdInvalido() throws Exception {
            VideoDTO videoDTOFake = gerarVideoDTOMock();
            ObjectId id = ObjectId.get();
            when(videoService.updateVideoById(any(ObjectId.class), any(VideoDTO.class)))
                    .thenThrow(VideoNotFoundException.class);

            mockMvc.perform(put("/videos/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(videoDTOFake)))
                    .andExpect(status().isNotFound());
            verify(videoService, times(1))
                    .updateVideoById(any(ObjectId.class), any(VideoDTO.class));
        }

        @Test
        void deveLancarExcecao_RemoverVideo_IdInvalido() throws Exception {
            ObjectId id = ObjectId.get();
            doThrow(VideoNotFoundException.class).when(videoService).deleteById(any(ObjectId.class));

            mockMvc.perform(delete("/videos/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            verify(videoService, times(1)).deleteById(id);
        }

        @Test
        void deveLancarExcecao_AlterarVideo_IdVazio() throws Exception {
            mockMvc.perform(put("/videos/")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isMethodNotAllowed());
            verify(videoService, times(0))
                    .updateVideoById(any(ObjectId.class), any(VideoDTO.class));
        }

        @Test
        void deveLancarExcecao_Payload_FormatoInvalido() throws Exception {
            mockMvc.perform(post("/videos")
                            .contentType(MediaType.APPLICATION_XML))
                    .andExpect(status().isUnsupportedMediaType());
        }
    }
}

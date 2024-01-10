package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.exceptions.ControllerExceptionHandler;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.services.VideoService;
import br.com.fiap.techchallenge.utils.VideoUtil;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.fiap.techchallenge.utils.JsonUtil.toJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VideoControllerTest {

    private AutoCloseable mock;
    private MockMvc mockMvc;
    @Mock
    private VideoService videoService;

    @BeforeEach
    void setupUp() {
        mock = MockitoAnnotations.openMocks(this);
        VideoController videoController = new VideoController(videoService);
        mockMvc = MockMvcBuilders.standaloneSetup(videoController)
                .setControllerAdvice(ControllerExceptionHandler.class)
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
            List<Video> videoFakerList = new ArrayList<>();
            when(videoService.findAll()).thenReturn(videoFakerList);

            mockMvc.perform(get("/videos"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoFakerList)));
            verify(videoService, times(1)).findAll();
        }

        @Test
        void deveListarTodosOsVideos() throws Exception {
            List<Video> videoFakerList = Arrays.asList(
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock(),
                    VideoUtil.gerarVideoMock()
            );
            when(videoService.findAll()).thenReturn(videoFakerList);

            List<VideoDTO> videoDTOList = videoFakerList.stream().map(Video::toVideoDTO).toList();
            mockMvc.perform(get("/videos"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoDTOList)));
            verify(videoService, times(1)).findAll();
        }


        @Test
        void deveBuscarVideoPorId() throws Exception {
            Video videoFake = VideoUtil.gerarVideoMock();
            String id = videoFake.getId();
            when(videoService.findById(anyString())).thenReturn(videoFake);

            VideoDTO videoDTO = videoFake.toVideoDTO();
            mockMvc.perform(get("/videos/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoDTO)));
            verify(videoService, times(1)).findById(anyString());
        }

        @Test
        void deveLancarExcecao_BuscarVideoPorId_VideoNaoEncontrado() throws Exception {
            String id = ObjectId.get().toHexString();
            when(videoService.findById(anyString())).thenThrow(VideoNotFoundException.class);

            mockMvc.perform(get("/videos/{id}", id))
                    .andExpect(status().isNotFound());
            verify(videoService, times(1)).findById(anyString());
        }
    }

    @Nested
    class InserirVideo {

        @Test
        void deveInserirVideo() throws Exception {
            Video videoFake = VideoUtil.gerarVideoMock();
            VideoDTO videoDTOFake = videoFake.toVideoDTO();
            when(videoService.insert(videoDTOFake)).thenReturn(videoFake);

            mockMvc.perform(post("/videos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(videoDTOFake)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(toJsonString(videoDTOFake)))
                    .andExpect(header().string("Location", containsString(videoFake.getId())));
            verify(videoService, times(1)).insert(any(VideoDTO.class));
        }

        @Test
        void deveLancarExcecao_InserirVideo_dadosInvalidos() throws Exception {
            VideoDTO videoDTOFake = new VideoDTO(null, null, null, null, null);

            mockMvc.perform(post("/videos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(videoDTOFake)))
                    .andExpect(status().isBadRequest());
            verify(videoService, times(0)).insert(any(VideoDTO.class));
        }
    }
}

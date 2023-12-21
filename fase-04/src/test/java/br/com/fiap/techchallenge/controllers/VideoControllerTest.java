package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.services.VideoService;
import br.com.fiap.techchallenge.utils.JsonUtil;
import br.com.fiap.techchallenge.utils.VideoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VideoControllerTest {

    private AutoCloseable mock;
    private MockMvc mockMvc;
    @Mock
    private VideoService videoService;

    @BeforeEach
    void setupUp() {
        mock = MockitoAnnotations.openMocks(this);
        VideoController videoController = new VideoController(videoService);
        mockMvc = MockMvcBuilders.standaloneSetup(videoController).build();
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
                    .andExpect(content().json(JsonUtil.toJsonString(videoFakerList)));
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
                    .andExpect(content().json(JsonUtil.toJsonString(videoDTOList)));
            verify(videoService, times(1)).findAll();
        }

        @Test
        void deveBuscarVideoPorId() {
            fail("teste não implementado");
        }

        @Test
        void deveLancarExcecao_BuscarVideoPorId_VideoNaoEncontrado() {
            fail("teste não implementado");
        }
    }
}

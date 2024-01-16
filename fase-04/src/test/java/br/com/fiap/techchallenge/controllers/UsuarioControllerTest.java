package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.exceptions.ControllerExceptionHandler;
import br.com.fiap.techchallenge.services.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static br.com.fiap.techchallenge.utils.JsonUtil.toJsonString;
import static br.com.fiap.techchallenge.utils.UsuarioUtil.gerarUsuarioMock;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private AutoCloseable mock;
    private MockMvc mockMvc;
    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setupUp() {
        mock = MockitoAnnotations.openMocks(this);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarUsuarios {

        @Test
        void deveRetornarListaVazia() throws Exception {
            Page<Usuario> usuarioPageMock = new PageImpl<>(Collections.emptyList());
            when(usuarioService.findAll(any(Pageable.class)))
                    .thenReturn(usuarioPageMock);

            mockMvc.perform(get("/usuarios"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(usuarioPageMock)));
            verify(usuarioService, times(1))
                    .findAll(any(Pageable.class));
        }

        @Test
        void deveListarTodosOsUsuarios() throws Exception {
            Page<Usuario> usuarioPageMock = new PageImpl<>(Arrays.asList(
                    gerarUsuarioMock(),
                    gerarUsuarioMock(),
                    gerarUsuarioMock()
            ));
            when(usuarioService.findAll(any(Pageable.class)))
                    .thenReturn(usuarioPageMock);

            Page<UsuarioDTO> usuariosDTOList = usuarioPageMock.map(Usuario::toUsuarioDTO);
            mockMvc.perform(get("/usuarios"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(usuariosDTOList)));
            verify(usuarioService, times(1))
                    .findAll(any(Pageable.class));
        }

        @Test
        void deveBuscarUsuarioPorId() throws Exception {
            Usuario usuarioMock = gerarUsuarioMock();
            String id = usuarioMock.getId();
            when(usuarioService.findById(anyString()))
                    .thenReturn(usuarioMock);

            UsuarioDTO usuarioDTO = usuarioMock.toUsuarioDTO();
            mockMvc.perform(get("/usuarios/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(usuarioDTO)));
            verify(usuarioService, times(1))
                    .findById(anyString());
        }
    }

    @Nested
    class InserirUsuario {

        @Test
        void deveInserirVideo() throws Exception {
            Usuario usuarioMock = gerarUsuarioMock();
            UsuarioDTO usuarioDTOMock = usuarioMock.toUsuarioDTO();
            when(usuarioService.insert(usuarioDTOMock))
                    .thenReturn(usuarioMock);

            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(usuarioDTOMock)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(toJsonString(usuarioDTOMock)))
                    .andExpect(header().string("Location", containsString(usuarioMock.getId())));
            verify(usuarioService, times(1))
                    .insert(any(UsuarioDTO.class));
        }
    }

//    @Nested
//    class AlterarVideo {
//
//        @Test
//        void deveAtualizarVideo() throws Exception {
//            Video videoInicial = gerarVideoMock();
//            VideoDTO videoForm = gerarVideoDTOMock();
//            String id = videoInicial.getId();
//
//            Video videoAtualizado = videoInicial.update(videoForm);
//            when(videoService.updateVideoById(id, videoForm))
//                    .thenReturn(videoAtualizado);
//
//            mockMvc.perform(put("/videos/{id}", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(toJsonString(videoForm)))
//                    .andExpect(status().isAccepted())
//                    .andExpect(content().json(toJsonString(videoAtualizado.toVideoDTO())))
//                    .andExpect(jsonPath("$.titulo").value(videoForm.titulo()))
//                    .andExpect(jsonPath("$.descricao").value(videoForm.descricao()));
//            verify(videoService, times(1))
//                    .updateVideoById(id, videoForm);
//        }
//    }

//    @Nested
//    class Exceptions {
//
//        @Test
//        void deveLancarExcecao_BuscarVideoPorId_VideoNaoEncontrado() throws Exception {
//            String id = ObjectId.get().toHexString();
//            when(videoService.findById(anyString()))
//                    .thenThrow(VideoNotFoundException.class);
//
//            mockMvc.perform(get("/videos/{id}", id))
//                    .andExpect(status().isNotFound());
//            verify(videoService, times(1))
//                    .findById(anyString());
//        }
//
//        @Test
//        void deveLancarExcecao_InserirVideo_dadosInvalidos() throws Exception {
//            VideoDTO videoDTOFake = new VideoDTO(null, null, null, null, null);
//
//            mockMvc.perform(post("/videos")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(toJsonString(videoDTOFake)))
//                    .andExpect(status().isBadRequest());
//            verify(videoService, times(0))
//                    .insert(any(VideoDTO.class));
//        }
//
//        @Test
//        void deveLancarExcecao_AlterarVideo_IdInvalido() throws Exception {
//            VideoDTO videoDTOFake = gerarVideoDTOMock();
//            String id = ObjectId.get().toHexString();
//            when(videoService.updateVideoById(anyString(), any(VideoDTO.class)))
//                    .thenThrow(VideoNotFoundException.class);
//
//            mockMvc.perform(put("/videos/{id}", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(toJsonString(videoDTOFake)))
//                    .andExpect(status().isNotFound());
//            verify(videoService, times(1))
//                    .updateVideoById(anyString(), any(VideoDTO.class));
//        }
//
//        @Test
//        void deveLancarExcecao_RemoverVideo_IdInvalido() throws Exception {
//            String id = ObjectId.get().toHexString();
//            doThrow(VideoNotFoundException.class).when(videoService).deleteById(anyString());
//
//            mockMvc.perform(delete("/videos/{id}", id)
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isNotFound());
//            verify(videoService, times(1)).deleteById(id);
//        }
//
//        @Test
//        void deveLancarExcecao_AlterarVideo_IdVazio() throws Exception {
//            mockMvc.perform(put("/videos/")
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isMethodNotAllowed());
//            verify(videoService, times(0))
//                    .updateVideoById(anyString(), any(VideoDTO.class));
//        }
//
//        @Test
//        void deveLancarExcecao_Payload_FormatoInvalido() throws Exception {
//            mockMvc.perform(post("/videos")
//                            .contentType(MediaType.APPLICATION_XML))
//                    .andExpect(status().isUnsupportedMediaType());
//        }
//    }
}

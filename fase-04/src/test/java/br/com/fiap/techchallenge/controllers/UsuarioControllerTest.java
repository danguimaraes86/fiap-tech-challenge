package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import br.com.fiap.techchallenge.exceptions.ControllerExceptionHandler;
import br.com.fiap.techchallenge.exceptions.FavoritoNaoEncontradoException;
import br.com.fiap.techchallenge.exceptions.UsuarioNotFoundException;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.services.UsuarioService;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static br.com.fiap.techchallenge.utils.JsonUtil.toJsonString;
import static br.com.fiap.techchallenge.utils.UsuarioUtil.gerarFavoritos;
import static br.com.fiap.techchallenge.utils.UsuarioUtil.gerarUsuarioMock;
import static br.com.fiap.techchallenge.utils.VideoUtil.gerarVideoMock;
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
            ObjectId id = usuarioMock.getId();
            when(usuarioService.findById(any(ObjectId.class)))
                    .thenReturn(usuarioMock);

            UsuarioDTO usuarioDTO = usuarioMock.toUsuarioDTO();
            mockMvc.perform(get("/usuarios/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(usuarioDTO)));
            verify(usuarioService, times(1))
                    .findById(id);
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
                    .andExpect(header().string("Location", containsString(usuarioMock.getId().toHexString())));
            verify(usuarioService, times(1))
                    .insert(any(UsuarioDTO.class));
        }
    }

    @Nested
    class AdicionarFavorito {

        @Test
        void deveAdicionarFavorito() throws Exception {
            Usuario usuarioMock = gerarUsuarioMock();
            List<String> favoritosMock = gerarFavoritos();
            when(usuarioService.adicionarFavoritos(any(ObjectId.class), anyList()))
                    .thenReturn(usuarioMock.adicionarFavorito(
                            favoritosMock.stream().map(ObjectId::new).toList()));

            mockMvc.perform(post("/usuarios/{id}/adicionarFavoritos", usuarioMock.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(favoritosMock)))
                    .andExpect(status().isAccepted())
                    .andExpect(content().json(toJsonString(usuarioMock.toUsuarioDTO())));
            verify(usuarioService, times(1)).adicionarFavoritos(any(ObjectId.class), anyList());
        }
    }

    @Nested
    class Recomendados {

        @Test
        void deveRetornarRecomendados() throws Exception {
            Usuario usuarioMock = gerarUsuarioMock();
            List<Video> videosMock = Collections.singletonList(gerarVideoMock());
            when(usuarioService.getVideosRecomendados(any(ObjectId.class)))
                    .thenReturn(videosMock);


            List<VideoDTO> videoDTOS = videosMock.stream().map(Video::toVideoDTO).toList();
            mockMvc.perform(get("/usuarios/{id}/recomendacoes", usuarioMock.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(toJsonString(videoDTOS)));
            verify(usuarioService, times(1)).getVideosRecomendados(any(ObjectId.class));
        }
    }

    @Nested
    class Exceptions {

        @Test
        void deveLancarExcecao_BuscarPorId_NaoEncontrado() throws Exception {
            ObjectId id = ObjectId.get();
            doThrow(UsuarioNotFoundException.class).when(usuarioService).findById(any());

            mockMvc.perform(get("/usuarios/{id}", id))
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1))
                    .findById(id);
        }

        @Test
        void deveLancarExcecao_InserirUsario_DadosInvalidos() throws Exception {
            mockMvc.perform(post("/usuarios"))
                    .andExpect(status().isBadRequest());
            verify(usuarioService, never())
                    .insert(any(UsuarioDTO.class));
        }

        @Test
        void deveLancarExcecao_BuscarUsario_FormatoInvalido() throws Exception {
            mockMvc.perform(get("/usuarios/{id}", "object_id"))
                    .andExpect(status().isBadRequest());
            verify(usuarioService, never())
                    .insert(any(UsuarioDTO.class));
        }

        @Test
        void deveLancarExcecao_AdicionarFavorito_UsuarioNaoEncontrado() throws Exception {
            ObjectId id = ObjectId.get();
            List<String> favoritosMock = Collections.singletonList(ObjectId.get().toHexString());
            doThrow(UsuarioNotFoundException.class).when(usuarioService).adicionarFavoritos(any(ObjectId.class), anyList());

            mockMvc.perform(post("/usuarios/{id}/adicionarFavoritos", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(favoritosMock))
                    )
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1))
                    .adicionarFavoritos(id, favoritosMock);
        }

        @Test
        void deveLancarExcecao_AdicionarFavorito_VideoNaoEncontrado() throws Exception {
            ObjectId id = ObjectId.get();
            List<String> favoritosMock = Collections.singletonList(ObjectId.get().toHexString());
            doThrow(VideoNotFoundException.class).when(usuarioService).adicionarFavoritos(any(ObjectId.class), anyList());

            mockMvc.perform(post("/usuarios/{id}/adicionarFavoritos", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(favoritosMock))
                    )
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1))
                    .adicionarFavoritos(id, favoritosMock);
        }

        @Test
        void deveLancarExcecao_AdicionarFavorito_ObjectId_FormatoInvalido() throws Exception {
            ObjectId id = ObjectId.get();
            List<String> favoritosMock = Collections.singletonList(ObjectId.get().toHexString());
            doThrow(FavoritoNaoEncontradoException.class).when(usuarioService).adicionarFavoritos(any(ObjectId.class), anyList());

            mockMvc.perform(post("/usuarios/{id}/adicionarFavoritos", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(favoritosMock)))
                    .andExpect(status().isBadRequest());
            verify(usuarioService, times(1))
                    .adicionarFavoritos(id, favoritosMock);
        }
    }
}

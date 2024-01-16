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
}

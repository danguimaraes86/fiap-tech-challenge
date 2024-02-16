package br.com.fiap.techchallenge.usuarios.services;

import br.com.fiap.techchallenge.usuarios.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.usuarios.repositories.UsuarioRespository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UsuarioServiceTest {

    AutoCloseable mocks;
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRespository usuarioRespository;

    private static Usuario getUsuarioMock() {
        return new Usuario(
                RandomString.make(),
                RandomString.make(),
                RandomString.make()
        );
    }

    @BeforeEach
    void setup() {
        mocks = openMocks(this);
        usuarioService = new UsuarioService(usuarioRespository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Nested
    class BuscarUsuarios {

        @Test
        void deveBuscarUsuarios_RetornaPageableVazio() {
            Page<Usuario> usuarioPageFake = new PageImpl<>(Collections.emptyList());
            when(usuarioRespository.findAll(any(Pageable.class))).thenReturn(usuarioPageFake);

            Page<Usuario> usuarioPage = usuarioService.findAllUsuarios(Pageable.unpaged());
            verify(usuarioRespository, times(1)).findAll(Pageable.unpaged());

            assertThat(usuarioPage).isInstanceOf(Page.class);
            assertThat(usuarioPage.getContent()).isEmpty();
        }

        @Test
        void deveBuscarUsuarios_RetornaPageable() {
            Page<Usuario> usuarioPageFake = new PageImpl<>(List.of(
                    mock(Usuario.class),
                    mock(Usuario.class),
                    mock(Usuario.class)));
            when(usuarioRespository.findAll(any(Pageable.class))).thenReturn(usuarioPageFake);

            Page<Usuario> usuarioPage = usuarioService.findAllUsuarios(Pageable.unpaged());
            verify(usuarioRespository, times(1)).findAll(Pageable.unpaged());

            assertThat(usuarioPage).isInstanceOf(Page.class);
            assertThat(usuarioPage.getTotalElements()).isEqualTo(usuarioPageFake.getTotalElements());
        }

        @Test
        void deveBuscarUsuarioPorId() {
            String id = UUID.randomUUID().toString();
            Usuario usuarioMock = getUsuarioMock();
            when(usuarioRespository.findById(anyString())).thenReturn(Optional.of(usuarioMock));

            Usuario produto = usuarioService.findUsuarioById(id);
            verify(usuarioRespository, times(1)).findById(id);

            assertThat(produto).isEqualTo(usuarioMock);
            assertThat(produto.getEmail()).isEqualTo(usuarioMock.getEmail());
            assertThat(produto.getNome()).isEqualTo(usuarioMock.getNome());
            assertThat(produto.getPassword()).isEqualTo(usuarioMock.getPassword());
        }
        @Nested
        class Exceptions {

            @Test
            void deveLancarExcecao_buscarUsuarioPorId_usuarioNaoEncontrado() {
                String id = UUID.randomUUID().toString();
                when(usuarioRespository.findById(anyString())).thenReturn(Optional.empty());

                assertThatThrownBy(() -> usuarioService.findUsuarioById(id))
                        .isInstanceOf(UsuarioNaoEncontradoException.class)
                        .hasMessage("usuario_id não encontrado");
                verify(usuarioRespository, times(1)).findById(anyString());
            }
        }
    }
}

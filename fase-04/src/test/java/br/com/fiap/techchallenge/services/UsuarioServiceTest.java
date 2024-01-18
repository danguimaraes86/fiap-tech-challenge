package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.exceptions.UsuarioNotFoundException;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.fiap.techchallenge.utils.UsuarioUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private AutoCloseable mocks;
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Nested
    class BuscarUsuarios {

        @Test
        void deveListarTodosOsUsuarios() {
            Page<Usuario> usuariosMock = new PageImpl<>(Arrays.asList(
                    mock(Usuario.class),
                    mock(Usuario.class),
                    mock(Usuario.class)
            ));
            when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuariosMock);

            Page<Usuario> usuarioPage = usuarioService.findAll(Pageable.unpaged());
            assertThat(usuarioPage.getContent())
                    .hasSize(3)
                    .contains(usuariosMock.getContent().get(Range.START_TO_START))
                    .doesNotContainNull()
                    .isEqualTo(usuariosMock.getContent());
            verify(usuarioRepository, times(1)).findAll(any(Pageable.class));
        }

        @Test
        void deveRetornarListaVazia() {
            Page<Usuario> usuariosMock = new PageImpl<>(Collections.emptyList());
            when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuariosMock);

            Page<Usuario> usuarios = usuarioService.findAll(Pageable.unpaged());
            assertThat(usuarios.getContent())
                    .isEmpty();
            verify(usuarioRepository, times(1)).findAll(any(Pageable.class));
        }

        @Test
        void deveBuscarUsuarioPorId() {
            Usuario usuarioMock = gerarUsuarioMock();
            String id = usuarioMock.getId();
            when(usuarioRepository.findById(anyString())).thenReturn(Optional.of(usuarioMock));

            Usuario usuarioById = usuarioService.findById(id);
            verify(usuarioRepository, times(1)).findById(id);
            assertThat(usuarioById)
                    .isInstanceOf(Usuario.class)
                    .isEqualTo(usuarioMock)
                    .isNotNull()
                    .isNotEqualTo(gerarUsuarioMock());
            assertThat(usuarioById.getId()).isEqualTo(id);
        }

        @Test
        void deveLancarExcecao_BuscarUsarioPorId_UsuarioNaoEncontrado() {
            Usuario usuarioMock = mock(Usuario.class);
            String id = usuarioMock.getId();
            when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usuarioService.findById(id))
                    .isInstanceOf(UsuarioNotFoundException.class)
                    .hasMessage("usuário não encontrado");
        }
    }

    @Nested
    class InserirUsuarios {

        @Test
        void deveInserirUsuario_RetornaUsuarioInserido() {
            Usuario usuarioMock = gerarUsuarioMock();
            UsuarioDTO usuarioDTOMock = gerarUsuarioDTOMock();
            when(usuarioRepository.insert(any(Usuario.class))).thenReturn(usuarioMock);

            Usuario usuarioNovo = usuarioService.insert(usuarioDTOMock);
            verify(usuarioRepository, times(1)).insert(any(Usuario.class));
            assertThat(usuarioNovo)
                    .isInstanceOf(Usuario.class)
                    .isEqualTo(usuarioMock);
            assertThat(usuarioNovo.getId()).isEqualTo(usuarioMock.getId());
            assertThat(usuarioNovo.getNome()).isEqualTo(usuarioMock.getNome());
        }
    }

    @Nested
    class AdicionarFavoritos {

        @Test
        void deveAdicionarFavoritos() {
            Usuario usuarioMock = gerarUsuarioMock();
            List<ObjectId> favoritosMock = gerarFavoritos();
            when(usuarioRepository.findById(anyString())).thenReturn(Optional.of(usuarioMock));
            when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

            Usuario usuario = usuarioService.adicionarFavoritos(usuarioMock.getId(), favoritosMock);
            verify(usuarioRepository, times(1)).findById(usuarioMock.getId());
            assertThat(usuario.getFavoritos())
                    .isNotEmpty()
                    .isEqualTo(favoritosMock)
                    .hasSize(favoritosMock.size());
            assertThat(usuario).isEqualTo(usuarioMock);
        }

        @Test
        void deveLancarExcecao_AdicionarFavoritos_UsuarioNaoEncontrado() {
            String id = ObjectId.get().toHexString();
            List<ObjectId> favoritosMock = gerarFavoritos();
            when(usuarioRepository.findById(anyString())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usuarioService.adicionarFavoritos(id, favoritosMock))
                    .isInstanceOf(UsuarioNotFoundException.class)
                    .hasMessage("usuário não encontrado");
            verify(usuarioRepository, times(1)).findById(id);
            verify(usuarioRepository, never()).save(any(Usuario.class));
        }
    }
}

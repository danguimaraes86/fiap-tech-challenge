package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.exceptions.FavoritoNaoEncontradoException;
import br.com.fiap.techchallenge.exceptions.UsuarioNotFoundException;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import br.com.fiap.techchallenge.repositories.VideoRepositoryReactive;
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
import static br.com.fiap.techchallenge.utils.VideoUtil.gerarVideoMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private AutoCloseable mocks;
    private UsuarioService usuarioService;
    private VideoService videoService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private VideoRepositoryReactive videoReactive;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        videoService = new VideoService(videoRepository, videoReactive);
        usuarioService = new UsuarioService(usuarioRepository, videoService);
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
            ObjectId id = usuarioMock.getId();
            when(usuarioRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(usuarioMock));

            Usuario usuarioById = usuarioService.findById(id);
            verify(usuarioRepository, times(1)).findById(id);
            assertThat(usuarioById)
                    .isInstanceOf(Usuario.class)
                    .isEqualTo(usuarioMock)
                    .isNotNull()
                    .isNotEqualTo(gerarUsuarioMock());
            assertThat(usuarioById.getId()).isEqualTo(id);
        }
    }

    @Nested
    class InserirUsuarios {

        @Test
        void deveInserirUsuario_RetornaUsuarioInserido() {
            Usuario usuarioMock = gerarUsuarioMock();
            UsuarioDTO usuarioDTOMock = gerarUsuarioDTOMock();
            when(usuarioRepository.insert(any(Usuario.class))).thenReturn(usuarioMock);
            when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

            Usuario usuarioNovo = usuarioService.insert(usuarioDTOMock);
            verify(usuarioRepository, times(1)).insert(any(Usuario.class));
            verify(usuarioRepository, times(1)).save(any(Usuario.class));
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
            List<Video> videoListMock = Collections.singletonList(gerarVideoMock());
            List<String> favoritosMock = videoListMock.stream().map(Video::getId).map(ObjectId::toHexString).toList();

            when(usuarioRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(usuarioMock));
            when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);
            when(videoRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(videoListMock.get(0)));

            Usuario usuario = usuarioService.adicionarFavoritos(usuarioMock.getId(), favoritosMock);
            verify(usuarioRepository, times(1)).findById(usuarioMock.getId());
            verify(videoRepository, times(1)).findById(any(ObjectId.class));
            assertThat(usuario.getFavoritos())
                    .isNotEmpty()
                    .isEqualTo(favoritosMock.stream().map(ObjectId::new).toList())
                    .hasSize(favoritosMock.size());
            assertThat(usuario).isEqualTo(usuarioMock);
        }
    }

    @Nested
    class Recomendados {

        @Test
        void deveRetornarListaRecomendados() {
            Usuario usuarioMock = gerarUsuarioMock();
            List<Video> videoListMock = Collections.singletonList(gerarVideoMock());
            List<ObjectId> idList = videoListMock.stream().map(Video::getId).toList();

            when(usuarioRepository.findById(any(ObjectId.class)))
                    .thenReturn(Optional.of(usuarioMock.adicionarFavorito(idList)));
            when(videoRepository.findAllById(idList)).thenReturn(videoListMock);
            when(videoRepository.findByCategoria(anyString())).thenReturn(videoListMock);

            List<Video> videoList = usuarioService.getVideosRecomendados(usuarioMock.getId());
            verify(usuarioRepository, times(1)).findById(usuarioMock.getId());
            verify(videoRepository, times(1)).findAllById(anyList());
            assertThat(videoList)
                    .isNotEmpty()
                    .isEqualTo(videoListMock)
                    .hasSize(videoListMock.size());
        }
    }

    @Nested
    class Exceptions {

        @Test
        void deveLancarExcecao_BuscarUsarioPorId_UsuarioNaoEncontrado() {
            ObjectId id = ObjectId.get();
            when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usuarioService.findById(id))
                    .isInstanceOf(UsuarioNotFoundException.class)
                    .hasMessage(String.format("usuario_id %s não encontrado", id));
        }

        @Test
        void deveLancarExcecao_AdicionarFavoritos_UsuarioNaoEncontrado() {
            ObjectId id = ObjectId.get();
            List<String> favoritosMock = gerarFavoritos();
            when(usuarioRepository.findById(any(ObjectId.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usuarioService.adicionarFavoritos(id, favoritosMock))
                    .isInstanceOf(UsuarioNotFoundException.class)
                    .hasMessage(String.format("usuario_id %s não encontrado", id));
            verify(usuarioRepository, times(1)).findById(id);
            verify(usuarioRepository, never()).save(any(Usuario.class));
        }

        @Test
        void deveLancarExcecao_AdicionarFavoritos_ObjectId_FormatoIncorreto() {
            Usuario usuarioMock = gerarUsuarioMock();
            ObjectId id = usuarioMock.getId();
            List<String> favoritosMock = Collections.singletonList(
                    "objeto_aleatorio"
            );
            when(usuarioRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(usuarioMock));

            assertThatThrownBy(() -> usuarioService.adicionarFavoritos(id, favoritosMock))
                    .isInstanceOf(FavoritoNaoEncontradoException.class)
                    .hasMessage(
                            String.format("video_id %s com formato incorreto", favoritosMock.get(0))
                    );
            verify(usuarioRepository, times(1)).findById(id);
            verify(usuarioRepository, never()).save(any(Usuario.class));
        }

        @Test
        void deveLancarExcecao_AdicionarFavoritos_VideoNaoEncontrado() {
            Usuario usuarioMock = gerarUsuarioMock();
            ObjectId id = usuarioMock.getId();
            List<String> favoritosMock = Collections.singletonList(
                    ObjectId.get().toHexString()
            );
            when(usuarioRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(usuarioMock));
            when(videoService.findAllById(anyList())).thenReturn(Collections.emptyList());

            assertThatThrownBy(() -> usuarioService.adicionarFavoritos(usuarioMock.getId(), favoritosMock))
                    .isInstanceOf(VideoNotFoundException.class)
                    .hasMessage(
                            String.format("video_id %s não encontrado", favoritosMock.get(0))
                    );
            verify(usuarioRepository, times(1)).findById(id);
            verify(usuarioRepository, never()).save(any(Usuario.class));
        }
    }
}

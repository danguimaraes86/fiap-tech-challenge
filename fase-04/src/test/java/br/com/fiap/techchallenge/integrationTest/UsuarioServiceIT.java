package br.com.fiap.techchallenge.integrationTest;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.services.UsuarioService;
import br.com.fiap.techchallenge.services.VideoService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static br.com.fiap.techchallenge.utils.UsuarioUtil.gerarUsuarioDTOMock;
import static br.com.fiap.techchallenge.utils.VideoUtil.gerarVideoDTOMock;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("integration")
class UsuarioServiceIT {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    void deveRetornarListaVazia() {
        var listaVideos = usuarioRepository.findAll();
        assertThat(listaVideos).isEmpty();
    }

    @Test
    void deveRetornarUsuarios() {
        List<UsuarioDTO> usuarioDTOList = Collections.singletonList(gerarUsuarioDTOMock());
        usuarioDTOList.forEach(usuarioDTO -> usuarioService.insert(usuarioDTO));

        var listaVideos = usuarioRepository.findAll();
        assertThat(listaVideos).hasSizeGreaterThanOrEqualTo(usuarioDTOList.size());
    }

    @Test
    void deveResgistrarUmUsuario() {
        UsuarioDTO usuarioDTOMock = gerarUsuarioDTOMock();
        var usuario = usuarioService.insert(usuarioDTOMock);

        assertThat(usuario).isInstanceOf(Usuario.class).isNotNull();
        assertThat(usuario.getNome()).isEqualTo(usuarioDTOMock.nome());
        assertThat(usuario.getFavoritos()).isEqualTo(usuarioDTOMock.favoritos().stream().map(ObjectId::new).toList());
    }

    @Test
    void deveBuscarPorUmUsuario() {
        UsuarioDTO videoMock = gerarUsuarioDTOMock();
        Usuario usuario = usuarioService.insert(videoMock);

        var usuarioBuscado = usuarioService.findById(usuario.getId());
        assertThat(usuarioBuscado).isNotNull();
        assertThat(usuarioBuscado.getId()).isEqualTo(usuario.getId());
    }

    @Test
    void deveAdicionarUmFavorito() {
        UsuarioDTO usuarioDTOMock = gerarUsuarioDTOMock();
        Usuario usuario = usuarioService.insert(usuarioDTOMock);
        Video video = videoService.insert(gerarVideoDTOMock());

        Usuario usuarioComFavorito = usuarioService.adicionarFavoritos(
                usuario.getId(),
                Collections.singletonList(video.getId().toHexString())
        );
        assertThat(usuarioComFavorito.getFavoritos().get(0)).isEqualTo(
                video.getId()
        );
    }
}

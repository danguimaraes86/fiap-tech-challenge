package br.com.fiap.techchallenge.integrationTest;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import br.com.fiap.techchallenge.services.VideoService;
import br.com.fiap.techchallenge.utils.VideoUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.couchbase.AutoConfigureDataCouchbase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class VideoIntegrationTest {

    @Autowired
    private VideoService service;;

    @Autowired
    private VideoRepository repository;

    @Test
    public void deveListarTodosOsVideos(){
        var listaVideos = repository.findAll();
        assertThat(listaVideos).hasSizeGreaterThan(0);

    }

    @Test
    public void deveResgistrarUmVideo(){
        VideoDTO video = new VideoDTO("Missão Impossível", "EthanHunt tentsalvar novamente o mundo.", "http://mockflix.com/missaoImpossivel", LocalDateTime.now().minusMinutes(5), LocalDateTime.now());
        var videoRegistrado = service.insert(video);

        assertThat(videoRegistrado)
                .isInstanceOf(Video.class)
                .isNotNull();
        assertThat(videoRegistrado.getUrl()).isEqualTo(video.url());
        assertThat(videoRegistrado.getTitulo()).isEqualTo(video.titulo());

    }

    @Test
    public void deveBuscarPorUmVideo(){
        var id = "b8fe1cc8-ca7c-425d-ae69-42f7efe93d24";
        var videoEncontrado = service.findById(id);
        assertThat(videoEncontrado).isNotNull();
        assertThat(videoEncontrado.getId()).isEqualTo(id);

    }

    @Test
    public void deveRemoverUmVideo(){
        var id = "b8fe1cc8-ca7c-425d-ae69-42f7efe93d24";
        service.deleteById("b8fe1cc8-ca7c-425d-ae69-42f7efe93d24");
        var video = repository.findById("b8fe1cc8-ca7c-425d-ae69-42f7efe93d24");
        assertThat(video).isEmpty();

    }

    @Test
    public void deveAtualizarUmVideo(){
        VideoDTO videoDTO = new VideoDTO("Jogo dos Tronos", "Uma série sobre a Geurra dos tronos.", "http://mockflix.com/gameOfThrones", LocalDateTime.now().minusMinutes(5), LocalDateTime.now());
        var id = "b8fe1cc8-ca7c-425d-ae69-42f7efe93d24";
        var videoEncontrado = repository.findById(id);
        var videoAlterado = service.updateVideoById(id, videoDTO);
        assertThat(videoAlterado.getTitulo()).isNotEqualTo(videoEncontrado.get().getTitulo());

    }

}

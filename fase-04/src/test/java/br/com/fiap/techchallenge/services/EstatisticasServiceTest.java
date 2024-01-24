package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.dtos.EstatisticaDTO;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static br.com.fiap.techchallenge.utils.UsuarioUtil.gerarFavoritos;
import static br.com.fiap.techchallenge.utils.UsuarioUtil.gerarUsuarioMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EstatisticasServiceTest {

    private AutoCloseable mocks;
    private EstatisticasService estatisticasService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private VideoRepository videoRepository;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        estatisticasService = new EstatisticasService(videoRepository, usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void deveRetornarEstatisticas() {
        List<Usuario> usuarioListMock = Collections.singletonList(gerarUsuarioMock());
        usuarioListMock.get(0).adicionarFavorito(gerarFavoritos().stream().map(ObjectId::new).toList());
        when(videoRepository.count()).thenReturn(10L);
        when(usuarioRepository.findAll()).thenReturn(usuarioListMock);

        EstatisticaDTO estatisticaDTO = estatisticasService.showEstatisticas();
        assertThat(estatisticaDTO).isInstanceOf(EstatisticaDTO.class);
        assertThat(estatisticaDTO.qntTotalVideos()).isGreaterThanOrEqualTo(10L);
        assertThat(estatisticaDTO.qntVideosFavoritos())
                .isGreaterThanOrEqualTo(usuarioListMock.get(0).getFavoritos().size());
    }

}

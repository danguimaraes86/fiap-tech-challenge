package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.dtos.EstatisticaDTO;
import br.com.fiap.techchallenge.services.EstatisticasService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class EstatisticasControllerTest {

    private AutoCloseable mock;
    @Mock
    private EstatisticasService estatisticasService;
    @InjectMocks
    private EstatisticasController estatisticasController;

    @BeforeEach
    void setupUp() {
        mock = MockitoAnnotations.openMocks(this);
        estatisticasController = new EstatisticasController(estatisticasService);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void deveRetornarEstatisticas() throws Exception {
        EstatisticaDTO estatisticaMock = new EstatisticaDTO(10, 7, 2.4);
        when(estatisticasService.showEstatisticas()).thenReturn(estatisticaMock);

        StepVerifier.create(estatisticasController.showEstatisticas())
                .expectSubscription()
                .expectNextMatches(response ->
                        response.equals(estatisticaMock) &&
                                response.qntTotalVideos() == 10 &&
                                response.qntVideosFavoritos() == 7 &&
                                response.mediaVizualizacoes() == 2.4
                )
                .verifyComplete();
    }
}

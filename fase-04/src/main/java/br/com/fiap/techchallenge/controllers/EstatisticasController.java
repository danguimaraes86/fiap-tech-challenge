package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.dtos.EstatisticaDTO;
import br.com.fiap.techchallenge.services.EstatisticasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final EstatisticasService estatisticasService;

    @GetMapping
    public Mono<EstatisticaDTO> showEstatisticas() {
        return Mono.just(estatisticasService.showEstatisticas());
    }
}

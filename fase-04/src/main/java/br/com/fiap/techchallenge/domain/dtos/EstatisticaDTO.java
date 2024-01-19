package br.com.fiap.techchallenge.domain.dtos;

public record EstatisticaDTO(
        long qntTotalVideos,
        long qntVideosFavoritos,
        Double mediaVizualizacoes
) {
}

package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.EstatisticaDTO;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EstatisticasService {

    private final VideoRepository videoRepository;
    private final UsuarioRepository usuarioRepository;

    public EstatisticaDTO showEstatisticas() {
        return new EstatisticaDTO(
                videoRepository.count(),
                calcularVideosFavoritos(),
                calcularMediaViews()
        );
    }

    private long calcularVideosFavoritos() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        Set<ObjectId> videosFavoritados = usuarioList.stream()
                .flatMap(usuario -> usuario.getFavoritos()
                        .stream().distinct()).collect(Collectors.toSet());
        return videosFavoritados.size();
    }

    private double calcularMediaViews() {
        return videoRepository.findAll()
                .stream().mapToDouble(Video::getVisualizacoes).average().orElse(0.0);
    }
}

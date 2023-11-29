package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    public Video insert(VideoDTO videoDTO) {
        return videoRepository.insert(videoDTO.toEntity());
    }

    public Video findById(String id) {
        return videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException("video não encontrado")
        );
    }
}

package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public Page<Video> findAll(Pageable pageRequest) {
        return videoRepository.findAll(pageRequest);
    }

    public Video insert(VideoDTO videoDTO) {
        return videoRepository.insert(
                new Video(videoDTO.titulo(), videoDTO.descricao(), videoDTO.url(), LocalDateTime.now())
        );
    }

    public Video findById(String id) {
        return videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException("video não encontrado")
        );
    }

    public void deleteById(String id) {
        Video video = findById(id);
        videoRepository.delete(video);
    }

    public Video updateVideoById(String id, VideoDTO videoDTO) {
        Video video = findById(id);
        video.update(videoDTO);
        return videoRepository.save(video);
    }
}

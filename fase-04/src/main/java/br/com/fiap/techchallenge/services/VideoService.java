package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import br.com.fiap.techchallenge.exceptions.VideoNotFoundException;
import br.com.fiap.techchallenge.repositories.VideoRepository;
import br.com.fiap.techchallenge.repositories.VideoRepositoryReactive;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoRepositoryReactive videoReactive;

    public Page<Video> findAll(Pageable pageRequest) {
        return videoRepository.findAll(pageRequest);
    }

    public List<ObjectId> findAllById(List<ObjectId> videos) {
        return videoRepository.findAllById(videos)
                .stream().map(Video::getId).toList();
    }

    public Video insert(VideoDTO videoDTO) {
        return videoRepository.insert(
                new Video(videoDTO.titulo(), videoDTO.descricao(), videoDTO.url(), LocalDateTime.now())
        );
    }

    public Video findById(ObjectId id) {
        return videoRepository.findById(id).orElseThrow(
                () -> new VideoNotFoundException(
                        String.format("video_id %s não encontrado", id)
                )
        );
    }

    public void deleteById(ObjectId id) {
        Video video = findById(id);
        videoRepository.delete(video);
    }

    public Video updateVideoById(ObjectId id, VideoDTO videoDTO) {
        Video video = findById(id);
        video.update(videoDTO);
        return videoRepository.save(video);
    }

    public Page<Video> findByAtributo(String titulo, LocalDateTime dataPublicacao, Pageable pageable) {
        return videoRepository.findVideoByTituloLikeIgnoreCaseAndDataPublicacaoBefore(
                titulo, dataPublicacao, pageable
        );
    }

    public void addVisualizacao(ObjectId id) {
        videoRepository.save(findById(id).addVisualizacao());
    }

    public Mono<Video> watchVideo(ObjectId id) {
        addVisualizacao(id);
        return videoReactive.findById(id);
    }
}

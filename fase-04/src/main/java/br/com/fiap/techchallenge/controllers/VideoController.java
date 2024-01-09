package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/videos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<List<VideoDTO>> findAllVideos() {
        List<Video> videoList = videoService.findAll();
        List<VideoDTO> videoDTOList = videoList.stream().map(Video::toVideoDTO).toList();
        return ResponseEntity.ok(videoDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> findById(@PathVariable String id) {
        Video video = videoService.findById(id);
        return ResponseEntity.ok(video.toVideoDTO());
    }
}

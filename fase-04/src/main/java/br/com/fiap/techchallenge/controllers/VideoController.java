package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.services.VideoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<VideoDTO> insertVideo(@RequestBody @Valid VideoDTO videoForm) {
        Video video = videoService.insert(videoForm);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(video.getId()).toUri();
        return ResponseEntity.created(location).body(video.toVideoDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoDTO> updateVideo(@PathVariable String id, @RequestBody VideoDTO videoForm) {
        Video videoAtualizado = videoService.updateVideoById(id, videoForm);
        return ResponseEntity.accepted().body(videoAtualizado.toVideoDTO());
    }
}

package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import br.com.fiap.techchallenge.services.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/videos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<Page<VideoDTO>> findAllVideos(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        Page<VideoDTO> videoDTOPage = videoService
                .findAll(PageRequest.of(page, size))
                .map(Video::toVideoDTO);
        return ResponseEntity.ok(videoDTOPage);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable String id) {
        videoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

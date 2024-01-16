package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

    Page<Video> findVideoByTituloLikeIgnoreCaseAndDataPublicacaoBefore(
            String titulo,
            LocalDateTime dataPublicacao,
            Pageable pageable
    );
}

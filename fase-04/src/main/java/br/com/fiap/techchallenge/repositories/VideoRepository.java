package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.Video;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoRepository extends MongoRepository<Video, ObjectId> {

    Page<Video> findVideoByTituloLikeIgnoreCaseAndDataPublicacaoBefore(
            String titulo,
            LocalDateTime dataPublicacao,
            Pageable pageable
    );

    List<Video> findByCategoria(String categoria);
}

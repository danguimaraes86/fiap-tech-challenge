package br.com.fiap.techchallenge.repositories;

import br.com.fiap.techchallenge.domain.Video;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepositoryReactive extends ReactiveMongoRepository<Video, ObjectId> {
}

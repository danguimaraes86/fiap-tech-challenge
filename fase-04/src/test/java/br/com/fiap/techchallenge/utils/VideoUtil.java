package br.com.fiap.techchallenge.utils;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.dtos.VideoDTO;
import com.github.javafaker.Faker;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Locale;

public class VideoUtil {

    public static Video gerarVideoMock() {
        Faker faker = new Faker(new Locale("pt_BR"));
        LocalDateTime publicacao = LocalDateTime.now();
        return new Video(
                ObjectId.get(),
                faker.name().name(),
                faker.witcher().quote(),
                "AÇÃO",
                faker.internet().url(),
                0, publicacao, publicacao
        );
    }

    public static VideoDTO gerarVideoDTOMock() {
        Faker faker = new Faker(new Locale("pt_BR"));
        LocalDateTime publicacao = LocalDateTime.now();
        return new VideoDTO(
                faker.name().name(),
                faker.witcher().quote(),
                "1",
                faker.internet().url(),
                publicacao, null
        );
    }
}

package br.com.fiap.techchallenge.utils;

import br.com.fiap.techchallenge.domain.Video;
import br.com.fiap.techchallenge.domain.VideoDTO;
import com.github.javafaker.Faker;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Locale;

public class VideoUtil {

    public static Video gerarVideoMock() {
        Faker faker = new Faker(new Locale("pt_BR"));

        return new Video(
                ObjectId.get().toHexString(),
                faker.witcher().witcher(),
                faker.witcher().school(),
                faker.witcher().quote(),
                LocalDateTime.now()
        );
    }

    public static VideoDTO gerarVideoDTOMock() {
        Faker faker = new Faker(new Locale("pt_BR"));

        return new VideoDTO(
                faker.witcher().witcher(),
                faker.witcher().school(),
                faker.witcher().quote(),
                LocalDateTime.now()
        );
    }
}

package br.com.fiap.techchallenge.utils;

import br.com.fiap.techchallenge.domain.Video;
import com.github.javafaker.Faker;

import java.util.Locale;

public class VideoUtil {

    public static Video gerarVideoMock() {
        Faker faker = new Faker(new Locale("pt_BR"));

        return new Video(
                faker.witcher().witcher(),
                faker.witcher().school(),
                faker.witcher().quote()
        );
    }
}

package br.com.fiap.techchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ParkimetroApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkimetroApplication.class, args);
    }

}

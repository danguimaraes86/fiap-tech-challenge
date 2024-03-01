package br.com.fiap.techchallenge.msoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsOauthApplication.class, args);
    }

}

package br.com.fiap.techchallenge.produtos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsProdutosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProdutosApplication.class, args);
    }

}

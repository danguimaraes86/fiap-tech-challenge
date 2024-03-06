package br.com.fiap.techchallenge.msgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsGatewayApplication {

    private static final String USUARIO_BASE_URL = "http://localhost:3001/usuarios/";
    private static final String OAUTH_BASE_URL = "http://localhost:3001/oauth/";
    private static final String PRODUTOS_BASE_URL = "http://localhost:3002";

    public static void main(String[] args) {
        SpringApplication.run(MsGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(auth -> auth
                        .path("/**")
                        .uri(OAUTH_BASE_URL))
                .route(usuario -> usuario
                        .path("/**")
                        .uri(USUARIO_BASE_URL))
                .route(produto -> produto
                        .path("/produtos/**")
                        .uri(PRODUTOS_BASE_URL))
                .build();
    }
}

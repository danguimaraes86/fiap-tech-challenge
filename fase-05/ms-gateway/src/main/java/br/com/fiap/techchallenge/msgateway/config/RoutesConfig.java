package br.com.fiap.techchallenge.msgateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Value("${gateway.url.ms-usuarios}")
    private String usuarioBaseUrl;

    @Value("${gateway.url.ms-oauth}")
    private String oauthBaseUrl;

    @Value("${gateway.url.ms-produtos}")
    private String produtosBaseUrl;

    @Value("${gateway.url.ms-carrinho}")
    private String carrinhosBaseUrl;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(auth -> auth.path("/oauth/**").uri(oauthBaseUrl))
                .route(usuario -> usuario.path("/usuarios/**").uri(usuarioBaseUrl))
                .route(produto -> produto.path("/produtos/**").uri(produtosBaseUrl))
                .route(carrinho -> carrinho.path("/carrinhos/**").uri(carrinhosBaseUrl))
                .build();
    }
}

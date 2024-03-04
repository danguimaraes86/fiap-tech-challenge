package br.com.fiap.techchallenge.carrinho.functions.webRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpMethod;


public class WebClientLinkRequest {
    private static String key;
    private static final WebClient webClient = WebClient.builder().baseUrl("http://127.0.0.1:3002").build();

    public static <T> T requisitionGeneric(String uri, HttpMethod httpMethod, Object requestBody, Class<T> responseType) {
        System.out.println(uri);

        try {
            return webClient
                    .method(httpMethod)
                    .uri(uri)
                    .header("x-api-key", key)
                    .body(requestBody != null ? BodyInserters.fromValue(requestBody) : null)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }


//    public static Employee requisition(String uri, HttpMethod httpMethod, Employee employee) {
//        return webClient
//                .method(httpMethod)
//                .uri(uri)
//                .header("x-api-key", key)
//                .body(BodyInserters.fromValue(employee))
//                .retrieve()
//                .bodyToMono(Employee.class)
//                .block();
//    }

    }
}

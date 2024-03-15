package br.com.fiap.techchallenge.carrinho.feignclients.usuario;

public record Usuario(
        String email,
        String nome,
        String password,
        Role role
) {
}

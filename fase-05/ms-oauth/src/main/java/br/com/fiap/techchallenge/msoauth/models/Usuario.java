package br.com.fiap.techchallenge.msoauth.models;

public record Usuario(
        String email,
        String nome,
        String password,
        Role role
) {
}

package br.com.fiap.techchallenge.msoauth.controllers;

public record AuthRequest(
        String email,
        String password
) {
}

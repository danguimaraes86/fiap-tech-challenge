package br.com.fiap.techchallenge.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String mensagem) {
        super(mensagem);
    }
}

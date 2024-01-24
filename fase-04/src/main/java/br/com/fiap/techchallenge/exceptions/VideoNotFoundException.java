package br.com.fiap.techchallenge.exceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String mensagem) {
        super(mensagem);
    }
}

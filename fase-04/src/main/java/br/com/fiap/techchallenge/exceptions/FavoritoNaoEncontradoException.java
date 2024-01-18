package br.com.fiap.techchallenge.exceptions;

public class FavoritoNaoEncontradoException extends RuntimeException {
    public FavoritoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

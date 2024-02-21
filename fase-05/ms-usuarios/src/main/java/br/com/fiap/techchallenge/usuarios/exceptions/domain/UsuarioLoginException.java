package br.com.fiap.techchallenge.usuarios.exceptions.domain;

public class UsuarioLoginException extends IllegalArgumentException {
    public UsuarioLoginException(String mensagem) {
        super(mensagem);
    }
}

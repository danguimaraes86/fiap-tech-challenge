package br.com.fiap.techchallenge.usuarios.exceptions;

public class UsuarioLoginException extends IllegalArgumentException {
    public UsuarioLoginException(String mensagem) {
        super(mensagem);
    }
}

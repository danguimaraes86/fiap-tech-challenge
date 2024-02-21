package br.com.fiap.techchallenge.usuarios.exceptions.domain;

import java.util.NoSuchElementException;

public class UsuarioNaoEncontradoException extends NoSuchElementException {
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

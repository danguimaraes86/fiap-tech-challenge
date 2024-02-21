package br.com.fiap.techchallenge.usuarios.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class UsuarioJaCadastradoException extends DataIntegrityViolationException {
    public UsuarioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}

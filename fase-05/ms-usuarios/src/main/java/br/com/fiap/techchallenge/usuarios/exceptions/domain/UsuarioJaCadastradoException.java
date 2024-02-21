package br.com.fiap.techchallenge.usuarios.exceptions.domain;

import org.springframework.dao.DataIntegrityViolationException;

public class UsuarioJaCadastradoException extends DataIntegrityViolationException {
    public UsuarioJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}

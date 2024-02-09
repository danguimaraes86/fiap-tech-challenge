package br.com.fiap.techchallenge.produtos.exceptions.domain;

import org.springframework.dao.DataIntegrityViolationException;

public class PrecoAbaixoZeroException extends DataIntegrityViolationException {
    public PrecoAbaixoZeroException(String message) {
        super(message);
    }
}

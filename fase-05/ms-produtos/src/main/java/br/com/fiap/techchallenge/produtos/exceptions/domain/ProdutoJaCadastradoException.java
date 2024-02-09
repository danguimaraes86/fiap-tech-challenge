package br.com.fiap.techchallenge.produtos.exceptions.domain;

import org.springframework.dao.DataIntegrityViolationException;

public class ProdutoJaCadastradoException extends DataIntegrityViolationException {
    public ProdutoJaCadastradoException(String message) {
        super(message);
    }
}

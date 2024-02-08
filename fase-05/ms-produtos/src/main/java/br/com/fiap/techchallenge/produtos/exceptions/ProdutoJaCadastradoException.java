package br.com.fiap.techchallenge.produtos.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class ProdutoJaCadastradoException extends DataIntegrityViolationException {
    public ProdutoJaCadastradoException(String message) {
        super(message);
    }
}

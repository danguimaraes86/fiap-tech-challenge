package br.com.fiap.techchallenge.produtos.exceptions;

import java.util.NoSuchElementException;

public class ProdutoNaoEncontradoException extends NoSuchElementException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}

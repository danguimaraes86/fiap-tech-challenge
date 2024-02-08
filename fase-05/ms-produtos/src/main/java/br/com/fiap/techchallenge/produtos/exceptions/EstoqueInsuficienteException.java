package br.com.fiap.techchallenge.produtos.exceptions;

public class EstoqueInsuficienteException extends IllegalArgumentException {

    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}

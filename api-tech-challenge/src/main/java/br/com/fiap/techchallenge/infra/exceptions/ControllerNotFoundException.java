package br.com.fiap.techchallenge.infra.exceptions;

public class ControllerNotFoundException extends RuntimeException{

    public ControllerNotFoundException(String message){
        super(message);
    }
}

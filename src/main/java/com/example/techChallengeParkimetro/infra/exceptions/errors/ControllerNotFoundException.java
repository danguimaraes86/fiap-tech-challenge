package com.example.techChallengeParkimetro.infra.exceptions.errors;

public class ControllerNotFoundException extends RuntimeException {

    public ControllerNotFoundException(String message){
        super(message);
    }
}

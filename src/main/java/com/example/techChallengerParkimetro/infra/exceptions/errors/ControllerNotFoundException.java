package com.example.techChallengerParkimetro.infra.exceptions.errors;

public class ControllerNotFoundException extends RuntimeException {

    public ControllerNotFoundException(String message){
        super(message);
    }
}

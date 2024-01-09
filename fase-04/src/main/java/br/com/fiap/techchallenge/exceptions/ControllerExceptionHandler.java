package br.com.fiap.techchallenge.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<Void> videoNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}

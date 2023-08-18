package br.com.fiap.techchallenge.controller.exception;

import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.exceptions.DatabaseException;
import br.com.fiap.techchallenge.infra.exceptions.DefaultError;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    private DefaultError error = new DefaultError();
    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(ControllerNotFoundException exeption, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exeption.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<DefaultError> entityNotFound(DatabaseException exeption, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exeption.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidacaoForm> validation(MethodArgumentNotValidException exeption, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidacaoForm validacaoForm = new ValidacaoForm();
        validacaoForm.setTimestamp(Instant.now());
        validacaoForm.setStatus(status.value());
        validacaoForm.setError("Erro de Validação");
        validacaoForm.setMessage("Abaixo o Array de erros de validação");
        validacaoForm.setPath(request.getRequestURI());

        for(FieldError fieldError : exeption.getBindingResult().getFieldErrors()){
            validacaoForm.addMensagens(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(validacaoForm);
    }
}

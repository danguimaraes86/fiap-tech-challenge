package com.example.techChallengeParkimetro.infra.exceptions;

import com.example.techChallengeParkimetro.infra.exceptions.errors.ControllerNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final DefaultError error = new DefaultError();
    private Map<String, String> messageList = new HashMap<>();

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DefaultError> generalError(RuntimeException exception, HttpServletRequest request) {
        Map<String, String> messageList = new HashMap<>();
        messageList.put("erro", exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Erro de execução");
        error.setMessage(messageList);
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(ControllerNotFoundException exception, HttpServletRequest request) {
        messageList.put("erro", exception.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entidade não encontrada");
        error.setMessage(messageList);
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultError> methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        messageList = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("payload validation");
        error.setMessage(messageList);
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DefaultError> dataIntegrityViolationException(DataIntegrityViolationException exception, HttpServletRequest request) {
        messageList.put("campo", exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("database validation");
        error.setMessage(messageList);
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<DefaultError> noSuchElementException(NoSuchElementException exception, HttpServletRequest request) {
        messageList.put("campo", exception.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("database validation");
        error.setMessage(messageList);
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.error);
    }
}

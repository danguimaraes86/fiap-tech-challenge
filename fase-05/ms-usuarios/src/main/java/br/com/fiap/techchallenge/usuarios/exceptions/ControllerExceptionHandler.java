package br.com.fiap.techchallenge.usuarios.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static Map<String, String> getFieldErrors(MethodArgumentNotValidException exception) {
        return exception.getFieldErrors()
                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultError> methodArgumentNotValidException(
            MethodArgumentNotValidException exception, HttpServletRequest request
    ) {
        return ResponseEntity.unprocessableEntity().body(
                new DefaultError(
                        LocalDateTime.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        request.getContextPath(),
                        getFieldErrors(exception)
                )
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<DefaultError> missingServletRequestParameterException(
            MissingServletRequestParameterException exception, HttpServletRequest request
    ) {
        return ResponseEntity.badRequest().body(
                new DefaultError(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST,
                        request.getContextPath(),
                        Collections.singletonMap(
                                exception.getParameterName(), exception.getBody().getDetail())
                )
        );
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<DefaultError> usuarioNaoEncontradoException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UsuarioJaCadastradoException.class)
    public ResponseEntity<DefaultError> usuarioJaCadastradoException(
            UsuarioJaCadastradoException exception, HttpServletRequest request
    ) {
        return ResponseEntity.unprocessableEntity().body(
                new DefaultError(
                        LocalDateTime.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        request.getContextPath(),
                        Collections.singletonMap("email", exception.getMessage())
                ));
    }

    @ExceptionHandler(UsuarioLoginException.class)
    public ResponseEntity<DefaultError> usuarioLoginException(
            UsuarioLoginException exception, HttpServletRequest request
    ) {
        return ResponseEntity.unprocessableEntity().body(
                new DefaultError(
                        LocalDateTime.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        request.getContextPath(),
                        Collections.singletonMap("login", exception.getMessage())
                ));
    }
}

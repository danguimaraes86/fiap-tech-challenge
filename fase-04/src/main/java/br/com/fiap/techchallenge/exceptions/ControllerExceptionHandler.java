package br.com.fiap.techchallenge.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<Void> videoNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Void> usuarioNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(FavoritoNaoEncontradoException.class)
    public ResponseEntity<String> favoritoNotFoundException(Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> methodArgumentTypeMismatchException() {
        return ResponseEntity.badRequest().body("id com formato incorreto");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException() {
        return ResponseEntity.badRequest().body("dados do vídeo incompleto/incorreto");
    }
}

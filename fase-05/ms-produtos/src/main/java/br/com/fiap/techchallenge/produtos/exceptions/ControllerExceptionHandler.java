package br.com.fiap.techchallenge.produtos.exceptions;

import br.com.fiap.techchallenge.produtos.exceptions.domain.EstoqueInsuficienteException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoJaCadastradoException;
import br.com.fiap.techchallenge.produtos.exceptions.domain.ProdutoNaoEncontradoException;
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

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<DefaultError> produtoNaoEncontradoException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ProdutoJaCadastradoException.class)
    public ResponseEntity<DefaultError> produtoJaCadastradoException(
            ProdutoJaCadastradoException exception, HttpServletRequest request
    ) {
        return ResponseEntity.unprocessableEntity().body(
                new DefaultError(
                        LocalDateTime.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        request.getContextPath(),
                        Collections.singletonMap("nome", exception.getMessage())
                ));
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<DefaultError> estoqueInsuficienteException(
            EstoqueInsuficienteException exception, HttpServletRequest request
    ) {
        return ResponseEntity.unprocessableEntity().body(
                new DefaultError(
                        LocalDateTime.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        request.getContextPath(),
                        Collections.singletonMap("quantidade", exception.getMessage())
                ));
    }
}

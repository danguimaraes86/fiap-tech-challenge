//package br.com.fiap.techchallenge.infra.exceptions;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.format.DateTimeParseException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.NoSuchElementException;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
//        Map<String, String> errors = exception.getBindingResult().getFieldErrors()
//                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
//        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(DateTimeParseException.class)
//    public ResponseEntity<?> handleParseValidation() {
//        Map<String, String> errors = new HashMap<>();
//        errors.put("dataNascimento", "utilize o formato AAAA-MM-DD");
//        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<?> handleEntityNotFoundException() {
//        Map<String, String> errors = new HashMap<>();
//        errors.put("pessoa", "nenhum usuário com o ID informado.");
//        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.NOT_FOUND);
//    }
//
////    [TODO] Corrigir handler como todas as Entidades
////    @ExceptionHandler(NoSuchElementException.class)
////    public ResponseEntity<?> handleEntityNotFoundException() {
////        Map<String, String> errors = new HashMap<>();
////        errors.put("endereco", "nenhum endereço com o ID informado.");
////        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.NOT_FOUND);
////    }
////
////    @ExceptionHandler(NoSuchElementException.class)
////    public ResponseEntity<?> handleEntityNotFoundException() {
////        Map<String, String> errors = new HashMap<>();
////        errors.put("eletrodomestico", "nenhum eletrodoméstico com o ID informado.");
////        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.NOT_FOUND);
////    }
//}

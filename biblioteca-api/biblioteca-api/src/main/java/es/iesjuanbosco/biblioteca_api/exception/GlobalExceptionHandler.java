package es.iesjuanbosco.biblioteca_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
 * @RestControllerAdvice: Convierte esta clase en un "vigilante" global
 * que intercepta las excepciones de todos los controladores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Este método se activa cuando salta una validación (@Valid)
     * y captura la excepción MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        // Recorremos todos los errores que ha detectado Spring
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField(); // Ej: "isbn"
            String mensaje = error.getDefaultMessage();     // Ej: "El isbn debe de contener 13 dígitos"
            errores.put(campo, mensaje);
        });

        // Devolvemos un 400 Bad Request con el mapa de errores en el cuerpo
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}

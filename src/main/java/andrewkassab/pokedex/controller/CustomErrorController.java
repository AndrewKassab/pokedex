package andrewkassab.pokedex.controller;

import andrewkassab.pokedex.controller.exceptions.IdProvidedException;
import andrewkassab.pokedex.controller.exceptions.TypeNotValidException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomErrorController {

    @ExceptionHandler
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        var cause = e.getCause();
        if (cause instanceof ValueInstantiationException) {
            return ResponseEntity.badRequest().body(cause.getCause().getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception){

        List errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity handleForeignKeyNotFoundErrors(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}

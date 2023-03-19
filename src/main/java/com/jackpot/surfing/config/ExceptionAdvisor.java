package com.jackpot.surfing.config;

import com.jackpot.surfing.api.error.ErrorResponseEntity;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseEntity> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
            .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        return ErrorResponseEntity.toResponseEntity(HttpStatus.BAD_REQUEST, MethodArgumentNotValidException.class.getSimpleName(), errors.toString());
    }
}

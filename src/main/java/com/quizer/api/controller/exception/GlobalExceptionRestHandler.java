package com.quizer.api.controller.exception;

import com.quizer.domain.exception.InvalidCategoriesException;
import com.quizer.domain.exception.InvalidDifficultyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionRestHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(InvalidDifficultyException.class)
    public ResponseEntity<String> handleInvalidDifficultyException(InvalidDifficultyException ex) {
        String errorMessage = ex.getMessage();
        String responseBody = "{\"statusCode\": \"BadRequest\", \"error\": \"" + errorMessage + "\"}";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody);
    }

    @ExceptionHandler(InvalidCategoriesException.class)
    public ResponseEntity<String> handleInvalidCategoryException(InvalidCategoriesException ex) {
        String errorMessage = ex.getMessage();
        String responseBody = "{\"statusCode\": \"BadRequest\", \"error\": \"" + errorMessage + "\"}";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        String errorMessage = ex.getMessage();
        String responseBody = "{\"statusCode\": \"NotFound\", \"error\": \"" + errorMessage + "\"}";
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseBody);
    }
}

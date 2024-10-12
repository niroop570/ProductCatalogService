package org.example.productcatalogservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class RestControllerAdvisor {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleExceptions(ResponseStatusException exception) {
        return new ResponseEntity<>(exception.getMessage(),exception.getStatusCode());
    }
}

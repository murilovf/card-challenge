package com.klab.cardchallenge.exceptions.handlers;

import com.klab.cardchallenge.exceptions.GameNotFoundException;
import com.klab.cardchallenge.exceptions.InvalidNumberCardsException;
import com.klab.cardchallenge.dto.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidNumberCardsException.class)
    public ResponseEntity<StandardResponse> handleInvalidNumberCardsException(InvalidNumberCardsException ex) {
        StandardResponse error = new StandardResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<StandardResponse> handleGameNotFoundException(GameNotFoundException ex) {
        StandardResponse error = new StandardResponse(false, ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

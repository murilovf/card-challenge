package com.klab.cardchallenge.exceptions.handlers;

import com.klab.cardchallenge.exceptions.*;
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
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NameContainsSpacesException.class)
    public ResponseEntity<StandardResponse> handleNameContainsSpacesException(NameContainsSpacesException ex) {
        StandardResponse error = new StandardResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlayerLimitReachedException.class)
    public ResponseEntity<StandardResponse> handlePlayerLimitReachedException(PlayerLimitReachedException ex) {
        StandardResponse error = new StandardResponse(false, ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoPlayersInGameException.class)
    public ResponseEntity<StandardResponse> handleNoPlayersInGameException(NoPlayersInGameException ex) {
        StandardResponse error = new StandardResponse(false, ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(GameAlreadyFinishedException.class)
    public ResponseEntity<StandardResponse> handleGameAlreadyFinishedExceptionException(GameAlreadyFinishedException ex) {
        StandardResponse error = new StandardResponse(false, ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

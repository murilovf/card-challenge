package com.klab.cardchallenge.exceptions;

public class NoPlayersInGameException extends RuntimeException {
    public NoPlayersInGameException(String message) {
        super(message);
    }
}

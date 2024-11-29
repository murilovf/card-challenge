package com.klab.cardchallenge.exceptions;

public class NameContainsSpacesException extends RuntimeException {
    public NameContainsSpacesException(String message) {
        super(message);
    }
}

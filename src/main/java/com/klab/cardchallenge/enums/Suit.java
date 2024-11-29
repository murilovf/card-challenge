package com.klab.cardchallenge.enums;

import java.util.Arrays;

public enum Suit {
    HEARTS("H"),
    DIAMONDS("D"),
    CLUBS("C"),
    SPADES("S");

    private final String value;

    Suit(String value) {
        this.value = value;
    }

    public static Suit getSuitFromValue(String value) {
        return Arrays.stream(Suit.values()).filter(card -> card.getValue().equals(value)).findFirst().get();
    }

    public String getValue() {
        return value;
    }
}

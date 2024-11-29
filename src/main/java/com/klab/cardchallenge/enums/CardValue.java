package com.klab.cardchallenge.enums;

import java.util.Arrays;

public enum CardValue {
    ACE("A", 1),
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7",7),
    EIGHT("8",8),
    NINE("9", 9),
    TEN("0",10),
    JACK("J",11),
    QUEEN("Q", 12),
    KING("K", 13);

    private final String value;
    private final Integer score;

    CardValue(String value, Integer score) {
        this.value = value;
        this.score = score;
    }

    public static CardValue getCardValueFromValue(String cardValue) {
        return Arrays.stream(CardValue.values()).filter(card -> card.getValue().equals(cardValue)).findFirst().get();
    }

    public int getScore() {
        return score;
    }

    public String getValue() {
        return value;
    }
}

package com.klab.cardchallenge.entities;

import com.klab.cardchallenge.enums.CardValue;
import com.klab.cardchallenge.enums.Suit;

public class Card {

    private String code;
    private CardValue value;
    private Suit suit;

    public Card(CardValue value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public CardValue getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getCode() {
        return value.name().charAt(0) + suit.name().substring(0, 1);
    }
}

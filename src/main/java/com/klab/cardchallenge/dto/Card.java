package com.klab.cardchallenge.dto;

import com.klab.cardchallenge.enums.CardValue;
import com.klab.cardchallenge.enums.Suit;

public class Card {
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
}

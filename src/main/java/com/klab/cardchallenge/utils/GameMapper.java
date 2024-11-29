package com.klab.cardchallenge.utils;

import com.klab.cardchallenge.entities.Card;
import com.klab.cardchallenge.enums.CardValue;
import com.klab.cardchallenge.enums.Suit;
import com.klab.cardchallenge.responses.deckapi.CardResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CardMapper {

    public static Card toCard(String code) {
        // Lógica para converter o cardId (ex: '2H') em um objeto Card
        String valueStr = code.substring(0, code.length() - 1); // Ex: '2'
        String suitStr = code.substring(code.length() - 1);    // Ex: 'H'
        CardValue value = CardValue.getCardValueFromValue(valueStr);
        Suit suit = Suit.getSuitFromValue(suitStr);
        return new Card(value, suit);
    }

    public static List<String> toListCardCode(List<CardResponse> listCardResponse) {
        return listCardResponse.stream()
                .map(cardResponse -> cardResponse.code())
                .collect(Collectors.toList());
    }
}

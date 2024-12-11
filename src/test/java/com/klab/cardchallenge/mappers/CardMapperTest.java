package com.klab.cardchallenge.mappers;

import com.klab.cardchallenge.dto.Card;
import com.klab.cardchallenge.enums.CardValue;
import com.klab.cardchallenge.enums.Suit;
import com.klab.cardchallenge.responses.deckapi.CardResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CardMapperTest {

    @Test
    public void testToCard() {
        String cardCode = "2H";
        Card card = CardMapper.toCard(cardCode);

        assertEquals(CardValue.TWO, card.getValue());
        assertEquals(Suit.HEARTS, card.getSuit());
    }

    @Test
    public void testToListCardCode() {
        CardResponse cardResponse1 = new CardResponse("2H", null, null, null, null);
        CardResponse cardResponse2 = new CardResponse("0D", null, null, null, null);
        List<CardResponse> cardResponses = List.of(cardResponse1, cardResponse2);

        List<String> cardCodes = CardMapper.toListCardCode(cardResponses);

        assertEquals(2, cardCodes.size());
        assertTrue(cardCodes.contains("2H"));
        assertTrue(cardCodes.contains("0D"));
    }
}

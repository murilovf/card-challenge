package com.klab.cardchallenge.responses;

import java.time.LocalDateTime;
import java.util.List;

public record GameResponse(
        String deckId,
        LocalDateTime creationDate,
        List<PlayerResponse> players,
        List<PlayerResponse> winnersPlayers,
        Integer numberOfPlayers,
        Integer cardsPerHand,
        LocalDateTime finishDate
) {
}

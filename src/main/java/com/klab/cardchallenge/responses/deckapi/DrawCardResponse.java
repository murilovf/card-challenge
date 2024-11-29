package com.klab.cardchallenge.responses.deckapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DrawCardResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("deck_id") String deckId,
        @JsonProperty("cards") List<CardResponse> cards,
        @JsonProperty("remaining") Integer remaining
) {
}

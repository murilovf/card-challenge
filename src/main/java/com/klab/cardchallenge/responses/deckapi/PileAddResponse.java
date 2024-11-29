package com.klab.cardchallenge.responses.deckapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record PileAddResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("deck_id") String deckId,
        @JsonProperty("remaining") Integer remaining,
        @JsonProperty("piles") Map<String, PileResponse> piles
) {

}

package com.klab.cardchallenge.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewDeckResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("deck_id") String deckId,
        @JsonProperty("remaining") Integer remaining,
        @JsonProperty("shuffled") boolean shuffled
) {
}

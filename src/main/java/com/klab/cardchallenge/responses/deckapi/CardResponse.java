package com.klab.cardchallenge.responses.deckapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardResponse(
        @JsonProperty("code") String code,
        @JsonProperty("image") String image,
        @JsonProperty("images") CardImageResponse images,
        @JsonProperty("value") String value,
        @JsonProperty("suit") String suit
) {
}

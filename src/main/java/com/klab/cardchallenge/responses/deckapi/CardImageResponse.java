package com.klab.cardchallenge.responses.deckapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardImageResponse(
        @JsonProperty("svg") String svg,
        @JsonProperty("png") String png
) {
}

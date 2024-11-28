package com.klab.cardchallenge.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardResponse(
        @JsonProperty("code") String code,
        @JsonProperty("image") String image,
        @JsonProperty("images") CardImageResponse images,
        @JsonProperty("value") Integer value,
        @JsonProperty("suit") Integer suit
) {
}

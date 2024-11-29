package com.klab.cardchallenge.responses;

import java.util.List;

public record PlayerResponse(
        String name,
        List<String> cards,
        Integer score
) {
}

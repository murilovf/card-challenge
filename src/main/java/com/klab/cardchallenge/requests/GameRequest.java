package com.klab.cardchallenge.requests;

public record GameRequest(
        Integer cardsPerHand,
        Integer numberPlayers,
        Boolean withPlayers
) { }

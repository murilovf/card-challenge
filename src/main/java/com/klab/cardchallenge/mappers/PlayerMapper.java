package com.klab.cardchallenge.mappers;

import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.responses.PlayerResponse;

public class PlayerMapper {

    public static PlayerResponse toPlayerResponse(Player player) {
        return new PlayerResponse(player.getName(), player.getCardIds(), player.getScore());
    }
}

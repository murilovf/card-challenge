package com.klab.cardchallenge.utils;

import com.klab.cardchallenge.entities.Card;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.enums.CardValue;
import com.klab.cardchallenge.enums.Suit;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.PlayerResponse;
import com.klab.cardchallenge.responses.deckapi.CardResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    public static GameResponse toGameResponse(Game game) {
        List<PlayerResponse> players = game.getPlayers().stream()
                .map(player -> PlayerMapper.toPlayerResponse(player))
                .collect(Collectors.toList());

        List<PlayerResponse> winnersPlayers = game.getGameWinners().stream()
                .map(gameWinners -> PlayerMapper.toPlayerResponse(gameWinners.getPlayer()))
                .collect(Collectors.toList());

        return new GameResponse(game.getDeckId(),
                game.getCreationDate(),
                players,
                winnersPlayers,
                game.getNumberOfPlayers(),
                game.getCardsPerHand(),
                game.getFinishDate());
    }
}

package com.klab.cardchallenge.utils;

import com.klab.cardchallenge.entities.GameWinners;
import com.klab.cardchallenge.exceptions.*;
import com.klab.cardchallenge.requests.GameRequest;
import com.klab.cardchallenge.entities.Game;

import java.util.List;

public class ValidationUtils {

    public static void validateGameCreation(GameRequest gameRequest, Integer remaining) {
        //Calcula numero total de cartas necessárias para distribuir para os jogadores
        Integer numberCardsDealt = gameRequest.numberPlayers() * gameRequest.cardsPerHand();

        if (numberCardsDealt > remaining) {
            throw new InvalidNumberCardsException("Numero de cartas a ser distribuído é maior que o número de cartas do deck");
        }
    }

    public static void validateAddPlayerInGame(Game game, String playerName) {
        if (game.getFinishDate() != null) {
            throw new GameAlreadyFinishedException("Esse jogo já está finalizado");
        }

        if (playerName.contains(" ")) {
            throw new NameContainsSpacesException("Nome do jogador não deve ter espaços");
        }

        if (game.getPlayers().size() >= game.getNumberOfPlayers()) {
            throw new PlayerLimitReachedException("Já existe o número de jogadores necessários para o jogo");
        }
    }

    public static void validateFinishGame(List<GameWinners> gameWinnersByGameId, Game game) {
        //Verifica se já existe vencedores para o jogo, se existir esse jogo já está finalizado
        if (!ListUtils.isNullOrEmpty(gameWinnersByGameId)) {
            throw new GameAlreadyFinishedException("Esse jogo já está finalizado");
        }

        if (ListUtils.isNullOrEmpty(game.getPlayers())) {
            throw new NoPlayersInGameException("Ainda não existe jogadores nesse jogo para que possa ser finalizado");
        }
    }
}

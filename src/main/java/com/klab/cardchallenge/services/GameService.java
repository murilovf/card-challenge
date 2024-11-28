package com.klab.cardchallenge.services;

import com.klab.cardchallenge.dto.GameDTO;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.exceptions.InvalidNumberCardsException;
import com.klab.cardchallenge.integration.deckapi.ShuffleIntegration;
import com.klab.cardchallenge.repositories.GameRepository;
import com.klab.cardchallenge.responses.NewDeckResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameService {

    @Autowired
    private ShuffleIntegration shuffleIntegration;

    @Autowired
    private GameRepository gameRepository;

    public Game create(GameDTO gameRequest) {
        //Cria novo deck
        NewDeckResponse newDeckResponse = shuffleIntegration.newDeck(1);

        //Realiza validações para verificar se pode prosseguir com o jogo com as entradas enviadas
        validateGameCreation(gameRequest, newDeckResponse.remaining());

        Game game = new Game(
                newDeckResponse.deckId(),
                gameRequest.numberPlayers(),
                gameRequest.cardsPerHand(),
                null,
                LocalDateTime.now()
        );

        Game gameSaved = gameRepository.save(game);

        return gameSaved;
    }

    private void validateGameCreation(GameDTO gameRequest, Integer remaining) {
        Integer numberCardsDealt = gameRequest.numberPlayers() * gameRequest.cardsPerHand();
        if (numberCardsDealt > remaining) {
            throw new InvalidNumberCardsException("Numero de cartas a ser distribuído é maior que o número de cartas do deck");
        }
    }
}

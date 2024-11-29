package com.klab.cardchallenge.services;

import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.GameWinners;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.repositories.GameWinnersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameWinnersService {

    @Autowired
    private GameWinnersRepository gameWinnersRepository;

    public void save(List<Player> winnersPlayers, String gameId) {
        Game game = new Game(gameId);
        winnersPlayers.stream().forEach(player -> {
            GameWinners gameWinners = new GameWinners(game, player);
            gameWinnersRepository.save(gameWinners);
        });
    }

    public List<GameWinners> getGameWinnersByGameId(String gameId) {
        return gameWinnersRepository.findGameWinnersByGame_DeckId(gameId);
    }
}

package com.klab.cardchallenge.services;

import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.GameWinners;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.repositories.GameWinnersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GameWinnersService {

    @Autowired
    private GameWinnersRepository gameWinnersRepository;

    @Autowired
    private PlayerService playerService;

    public void save(String gameId) {
        //Busca lista de vencedores
        List<Player> winnersPlayers = getWinnersPlayers(gameId);

        Game game = new Game(gameId);
        winnersPlayers.stream().forEach(player -> {
            GameWinners gameWinners = new GameWinners(game, player);
            gameWinnersRepository.save(gameWinners);
        });
    }

    public List<Player> getWinnersPlayers(String gameId) {
        List<Player> players = playerService.findPlayersByGameId(gameId);

        //Busca o maior score entre todos os jogadores
        Integer topScore = players.stream()
                .mapToInt(player -> player.getScore())
                .max()
                .orElseThrow(() -> new NoSuchElementException("Lista de jogadores está vazia"));

        //Filtra os jogadores que tiveram o maior score
        List<Player> winnersPlayers = players.stream()
                .filter(player -> player.getScore() == topScore)
                .collect(Collectors.toList());

        return winnersPlayers;
    }

    public List<GameWinners> getGameWinnersByGameId(String gameId) {
        return gameWinnersRepository.findGameWinnersByGame_DeckId(gameId);
    }
}

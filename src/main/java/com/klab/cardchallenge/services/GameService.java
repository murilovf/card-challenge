package com.klab.cardchallenge.services;

import com.klab.cardchallenge.dto.GameDTO;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.GameWinners;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.exceptions.GameAlreadyFinishedException;
import com.klab.cardchallenge.exceptions.GameNotFoundException;
import com.klab.cardchallenge.exceptions.InvalidNumberCardsException;
import com.klab.cardchallenge.integration.deckapi.ShuffleIntegration;
import com.klab.cardchallenge.repositories.GameRepository;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.deckapi.NewDeckResponse;
import com.klab.cardchallenge.utils.GameMapper;
import com.klab.cardchallenge.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameWinnersService gameWinnersService;

    @Autowired
    private ShuffleIntegration shuffleIntegration;

    @Autowired
    private GameRepository gameRepository;

    public Game create(GameDTO gameRequest) {
        //Cria novo deck
        NewDeckResponse newDeckResponse = shuffleIntegration.newDeck(1);
        
        //Realiza validações para verificar se pode prosseguir com o jogo com as entradas enviadas
        validateGameCreation(gameRequest, newDeckResponse.remaining());

        //Criar player e distribuir cartas
        List<Player> players = playerService.createGamePlayers(newDeckResponse.deckId(), gameRequest);

        Game game = new Game(
                newDeckResponse.deckId(),
                gameRequest.numberPlayers(),
                gameRequest.cardsPerHand(),
                players,
                LocalDateTime.now()
        );

        Game gameSaved = gameRepository.save(game);

        return gameSaved;
    }

    private void validateGameCreation(GameDTO gameRequest, Integer remaining) {
        //Calcula numero total de cartas necessárias para distribuir para os jogadores
        Integer numberCardsDealt = gameRequest.numberPlayers() * gameRequest.cardsPerHand();

        if (numberCardsDealt > remaining) {
            throw new InvalidNumberCardsException("Numero de cartas a ser distribuído é maior que o número de cartas do deck");
        }
    }

    public GameResponse finish(String gameId) {
        //Verifica se já existe vencedores para o jogo, se existir esse jogo já está finalizado
        List<GameWinners> gameWinnersByGameId = gameWinnersService.getGameWinnersByGameId(gameId);
        if (!ListUtils.isNullOrEmpty(gameWinnersByGameId)) {
            throw new GameAlreadyFinishedException("Esse jogo já está finalizado");
        }

        //Busca lista de vencedores
        List<Player> winnersPlayers = getWinnersPlayers(gameId);

        //Salva o(s) vencedor(es) na tabela de game_winners
        gameWinnersService.save(winnersPlayers, gameId);

        //Seta a data de finalização do game e salva
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game não encontrado"));
        game.setFinishDate(LocalDateTime.now());
        Game gameSaved = gameRepository.save(game);

        return GameMapper.toGameResponse(gameSaved);
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
}

package com.klab.cardchallenge.services;

import com.klab.cardchallenge.requests.GameRequest;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.GameWinners;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.exceptions.GameNotFoundException;
import com.klab.cardchallenge.integration.deckapi.ShuffleIntegration;
import com.klab.cardchallenge.repositories.GameRepository;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.deckapi.NewDeckResponse;
import com.klab.cardchallenge.mappers.GameMapper;
import com.klab.cardchallenge.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public GameResponse create(GameRequest gameRequest) {
        //Cria novo deck
        NewDeckResponse newDeckResponse = shuffleIntegration.newDeck(1);

        //Realiza validações para verificar se pode prosseguir com o jogo com as entradas enviadas
        ValidationUtils.validateGameCreation(gameRequest, newDeckResponse.remaining());

        //Olha para variavel boolean withPlayers para decidir se cria o jogo já com os jogadores e suas cartas
        List<Player> players = new ArrayList<>();
        if (gameRequest.withPlayers() != null && gameRequest.withPlayers()) {
            //Criar jogadores necessários e distribuir cartas
            players = playerService.addAllPlayersInGame(newDeckResponse.deckId(), gameRequest);
        }

        Game game = new Game(
                newDeckResponse.deckId(),
                gameRequest.numberPlayers(),
                gameRequest.cardsPerHand(),
                players,
                LocalDateTime.now()
        );

        Game gameSaved = gameRepository.save(game);

        return GameMapper.toGameResponse(gameSaved);
    }

    public GameResponse finish(String gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game não encontrado"));
        List<GameWinners> gameWinnersByGameId = gameWinnersService.getGameWinnersByGameId(gameId);

        ValidationUtils.validateFinishGame(gameWinnersByGameId, game);

        //Salva o(s) vencedor(es) na tabela de game_winners
        gameWinnersService.save(gameId);

        //Seta a data de finalização do game e salva
        Game gameSaved = updateFinishData(game);

        return GameMapper.toGameResponse(gameSaved);
    }

    public Game updateFinishData(Game game) {
        game.setFinishDate(LocalDateTime.now());
        gameRepository.save(game);

        return gameRepository.findById(game.getDeckId()).orElseThrow(() -> new GameNotFoundException("Game não encontrado"));
    }

    public GameResponse findById(String gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game não encontrado"));
        return GameMapper.toGameResponse(game);
    }
}

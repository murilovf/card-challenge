package com.klab.cardchallenge.services;

import com.klab.cardchallenge.requests.GameRequest;
import com.klab.cardchallenge.requests.PlayerRequest;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.exceptions.*;
import com.klab.cardchallenge.integration.deckapi.CardIntegration;
import com.klab.cardchallenge.integration.deckapi.PileIntegration;
import com.klab.cardchallenge.repositories.GameRepository;
import com.klab.cardchallenge.repositories.PlayerRepository;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.PlayerResponse;
import com.klab.cardchallenge.responses.deckapi.DrawCardResponse;
import com.klab.cardchallenge.mappers.CardMapper;
import com.klab.cardchallenge.mappers.GameMapper;
import com.klab.cardchallenge.mappers.PlayerMapper;
import com.klab.cardchallenge.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlayerService {
    @Autowired
    private CardIntegration cardIntegration;

    @Autowired
    private PileIntegration pileIntegration;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    public GameResponse addPlayerInGame(PlayerRequest playerRequest) {
        Game game = gameRepository.findById(playerRequest.gameId()).orElseThrow((() -> new GameNotFoundException("Game não encontrado")));

        //Realiza as validações necessárias para criar o player no jogo
        ValidationUtils.validateAddPlayerInGame(game, playerRequest.name());

        //Cria o jogador e distribui as cartas para ele, conforme configuradas no jogo criado
        Player player = new Player();
        player.setName(playerRequest.name());
        drawCardsToPlayer(game.getDeckId(), game.getCardsPerHand(), player);

        //Adiciona jogador já com as cartas na lista de jogadores do jogo e salva o jogo
        game.getPlayers().add(player);
        Game gameSaved = gameRepository.save(game);

        return GameMapper.toGameResponse(gameSaved);
    }

    public List<Player> addAllPlayersInGame(String deckId, GameRequest gameRequest) {
        //Cria uma lista de jogadores vazia do tamanho definido para o jogo
        List<Player> players = Stream.generate(() -> new Player())
                .limit(gameRequest.numberPlayers())
                .collect(Collectors.toList());

        //Percorre essa lista vazia preenchendo com as informações de cada jogador
        players.stream().forEach(player -> {
            player.setName("Player " + players.indexOf(player) + 1);
            drawCardsToPlayer(deckId, gameRequest.cardsPerHand(), player);
        });

        return players;
    }

    private void drawCardsToPlayer(String deckId, Integer cardsPerHand, Player player) {
        //Retira cartas necessarias do deck
        DrawCardResponse drawCardResponse = cardIntegration.draw(deckId, cardsPerHand);

        player.setCardIds(CardMapper.toListCardCode(drawCardResponse.cards()));
        player.setGame(new Game(deckId));
        player.setCards(player.getCards());
        player.setScore(player.getScore());

        //Distribui cartas retiradas para o player criado
        pileIntegration.pileAdd(deckId, player.getName(), player.getCardIds());
    }

    public List<Player> findPlayersByGameId(String gameId) {
        return playerRepository.findPlayersByGame_DeckId(gameId);
    }

    public List<PlayerResponse> findByGameId(String gameId) {
        List<Player> players = findPlayersByGameId(gameId);

        return players.stream()
                .map(player -> PlayerMapper.toPlayerResponse(player))
                .collect(Collectors.toList());
    }
}

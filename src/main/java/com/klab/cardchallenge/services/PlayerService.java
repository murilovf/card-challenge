package com.klab.cardchallenge.services;

import com.klab.cardchallenge.dto.GameDTO;
import com.klab.cardchallenge.dto.PlayerDTO;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.integration.deckapi.CardIntegration;
import com.klab.cardchallenge.integration.deckapi.PileIntegration;
import com.klab.cardchallenge.repositories.PlayerRepository;
import com.klab.cardchallenge.responses.deckapi.DrawCardResponse;
import com.klab.cardchallenge.responses.deckapi.PileAddResponse;
import com.klab.cardchallenge.utils.CardMapper;
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

    public List<Player> createGamePlayers(String deckId, GameDTO gameRequest) {

        List<Player> players = Stream.generate(() -> new Player())
                .limit(gameRequest.numberPlayers())
                .collect(Collectors.toList());

        players.stream().forEach(player -> {
            //Retira cartas necessarias do deck
            DrawCardResponse drawCardResponse = cardIntegration.draw(deckId, gameRequest.cardsPerHand());

            player.setName("Player" + players.indexOf(player) + 1);
            player.setCardIds(CardMapper.toListCardCode(drawCardResponse.cards()));
            player.setGame(new Game(deckId));
            player.setCards(player.getCards());
            player.setScore(player.getScore());

            //Distribui cartas retiradas para o player criado
            PileAddResponse pileAddResponse = pileIntegration.pileAdd(deckId, player.getName(), player.getCardIds());
        });

        return players;
    }

    public Player addPlayerInGame(PlayerDTO playerRequest) {
        return new Player();
    }

    public List<Player> findPlayersByGameId(String gameId) {
        return playerRepository.findPlayersByGame_DeckId(gameId);
    }
}

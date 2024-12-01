package com.klab.cardchallenge.services;

import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.GameWinners;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.exceptions.GameNotFoundException;
import com.klab.cardchallenge.integration.deckapi.ShuffleIntegration;
import com.klab.cardchallenge.repositories.GameRepository;
import com.klab.cardchallenge.requests.GameRequest;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.deckapi.NewDeckResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private PlayerService playerService;

    @Mock
    private GameWinnersService gameWinnersService;

    @Mock
    private ShuffleIntegration shuffleIntegration;

    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGameWithPlayers() {
        GameRequest gameRequest = new GameRequest(4, 5, true);
        NewDeckResponse newDeckResponse = new NewDeckResponse(true,"deck123", 52, false);

        Player player1 = new Player("player1");
        player1.setCardIds(List.of("AH", "6S"));
        Player player2 = new Player("player2");
        player2.setCardIds(List.of("AS", "6H"));

        List<Player> players = List.of(player1, player2);

        when(shuffleIntegration.newDeck(1)).thenReturn(newDeckResponse);
        when(playerService.addAllPlayersInGame(eq("deck123"), eq(gameRequest))).thenReturn(players);

        Game savedGame = new Game("deck123", 4, 5, players, LocalDateTime.now());
        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        GameResponse response = gameService.create(gameRequest);

        assertNotNull(response);
        verify(playerService).addAllPlayersInGame(eq("deck123"), eq(gameRequest));
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void testCreateGameWithoutPlayers() {
        GameRequest gameRequest = new GameRequest(4, 5, false);
        NewDeckResponse newDeckResponse = new NewDeckResponse(true,"deck123", 52, false);

        when(shuffleIntegration.newDeck(1)).thenReturn(newDeckResponse);

        Game savedGame = new Game("deck123", 4, 5, new ArrayList<>(), LocalDateTime.now());
        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        GameResponse response = gameService.create(gameRequest);

        assertNotNull(response);
        verify(playerService, never()).addAllPlayersInGame(anyString(), any(GameRequest.class));
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void testFinishGame() {
        String gameId = "game123";
        Player player = new Player();
        player.setName("player1");
        player.setCardIds(List.of("AH", "6S"));

        Game game = new Game("deck123", 4, 5, List.of(player), LocalDateTime.now());
        List<GameWinners> gameWinners = new ArrayList<>();

        when(gameRepository.findById(eq(gameId))).thenReturn(Optional.of(game));
        when(gameWinnersService.getGameWinnersByGameId(eq(gameId))).thenReturn(gameWinners);

        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameRepository.findById(eq(game.getDeckId()))).thenReturn(Optional.of(game));

        GameResponse response = gameService.finish(gameId);

        assertNotNull(response);
        verify(gameWinnersService).save(eq(gameId));
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameRepository, times(1)).findById(eq(game.getDeckId())); // Confirma a chamada no updateFinishData
    }

    @Test
    void testFinishGameThrowsGameNotFoundException() {
        String gameId = "game123";

        when(gameRepository.findById(eq(gameId))).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.finish(gameId));
    }

    @Test
    void testFindById() {
        String gameId = "game123";
        Game game = new Game("deck123", 4, 5, new ArrayList<>(), LocalDateTime.now());

        when(gameRepository.findById(eq(gameId))).thenReturn(Optional.of(game));

        GameResponse response = gameService.findById(gameId);

        assertNotNull(response);
        verify(gameRepository).findById(eq(gameId));
    }

    @Test
    void testFindByIdThrowsGameNotFoundException() {
        String gameId = "game123";

        when(gameRepository.findById(eq(gameId))).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.findById(gameId));
    }
}

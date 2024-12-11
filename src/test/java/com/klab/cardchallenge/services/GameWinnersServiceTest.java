package com.klab.cardchallenge.services;

import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.GameWinners;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.repositories.GameWinnersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameWinnersServiceTest {

    @InjectMocks
    private GameWinnersService gameWinnersService;

    @Mock
    private GameWinnersRepository gameWinnersRepository;

    @Mock
    private PlayerService playerService;

    private Game game;
    private Player player1;
    private Player player2;
    private List<Player> players;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        game = new Game("gameId1");
        player1 = new Player();
        player1.setScore(10);
        player1.setName("Player 1");
        player1.setCardIds(List.of("AH", "6S"));

        player2 = new Player();
        player2.setScore(10);
        player2.setName("Player 2");
        player2.setCardIds(List.of("AS", "6H"));

        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }

    @Test
    public void testSave() {
        when(playerService.findPlayersByGameId(anyString())).thenReturn(players);

        gameWinnersService.save("gameId1");

        verify(gameWinnersRepository, times(2)).save(any(GameWinners.class));
    }

    @Test
    public void testGetWinnersPlayers() {
        when(playerService.findPlayersByGameId(anyString())).thenReturn(players);

        List<Player> winners = gameWinnersService.getWinnersPlayers("gameId1");

        assertEquals(2, winners.size()); // Como ambos os jogadores têm o maior score, ambos são vencedores
    }

    @Test
    public void testGetWinnersPlayers_NoPlayersFound() {
        when(playerService.findPlayersByGameId(anyString())).thenReturn(new ArrayList<>()); // Retorna uma lista vazia

        assertThrows(NoSuchElementException.class, () -> gameWinnersService.getWinnersPlayers("gameId1"));
    }

    @Test
    public void testGetGameWinnersByGameId() {
        GameWinners gameWinner = new GameWinners(game, player1);
        when(gameWinnersRepository.findGameWinnersByGame_DeckId(anyString())).thenReturn(List.of(gameWinner));

        List<GameWinners> gameWinners = gameWinnersService.getGameWinnersByGameId("gameId1");

        assertNotNull(gameWinners);
        assertEquals(1, gameWinners.size());
        assertEquals(player1.getName(), gameWinners.get(0).getPlayer().getName());
    }
}

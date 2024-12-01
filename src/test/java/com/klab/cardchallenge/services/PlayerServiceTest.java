package com.klab.cardchallenge.services;

import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.exceptions.GameNotFoundException;
import com.klab.cardchallenge.integration.deckapi.CardIntegration;
import com.klab.cardchallenge.integration.deckapi.PileIntegration;
import com.klab.cardchallenge.repositories.GameRepository;
import com.klab.cardchallenge.repositories.PlayerRepository;
import com.klab.cardchallenge.requests.GameRequest;
import com.klab.cardchallenge.requests.PlayerRequest;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.PlayerResponse;
import com.klab.cardchallenge.responses.deckapi.DrawCardResponse;
import com.klab.cardchallenge.responses.deckapi.PileAddResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerServiceTest {

    @Mock
    private CardIntegration cardIntegration;

    @Mock
    private PileIntegration pileIntegration;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private PlayerService playerService;

    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        game = new Game();
        game.setDeckId("deck123");
        game.setCardsPerHand(5);
        game.setNumberOfPlayers(5);
        game.setPlayers(new ArrayList<>());
    }

    @Test
    void testAddPlayerInGame() {
        PlayerRequest playerRequest = new PlayerRequest("player1", "game123");
        when(gameRepository.findById(anyString())).thenReturn(Optional.of(game));
        when(cardIntegration.draw(anyString(), anyInt())).thenReturn(mock(DrawCardResponse.class));
        when(pileIntegration.pileAdd(anyString(), anyString(), anyList())).thenReturn(mock(PileAddResponse.class));
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        GameResponse gameResponse = playerService.addPlayerInGame(playerRequest);

        assertNotNull(gameResponse);
        assertEquals(gameResponse.deckId(), game.getDeckId());
        assertEquals(gameResponse.players().size(), 1);
        verify(gameRepository).save(game);
        verify(cardIntegration).draw(anyString(), eq(5));
    }

    @Test
    void testAddPlayerInGame_GameNotFound() {
        PlayerRequest playerRequest = new PlayerRequest("game123", "Player 1");
        when(gameRepository.findById(anyString())).thenReturn(Optional.empty());

        GameNotFoundException exception = assertThrows(GameNotFoundException.class, () -> playerService.addPlayerInGame(playerRequest));
        assertEquals("Game não encontrado", exception.getMessage());
    }

    @Test
    void testAddAllPlayersInGame() {
        GameRequest gameRequest = new GameRequest(5,3, true);
        when(cardIntegration.draw(anyString(), anyInt())).thenReturn(mock(DrawCardResponse.class));

        List<Player> players = playerService.addAllPlayersInGame("deck123", gameRequest);

        assertEquals(3, players.size());
        verify(cardIntegration, times(3)).draw(anyString(), eq(5)); // Verifica se o draw foi chamado para 3 jogadores
    }

    @Test
    void testFindByGameId() {
        String gameId = "game123";
        Player player = new Player();
        player.setName("Player 1");
        player.setCardIds(List.of("AH", "6S"));
        List<Player> players = Arrays.asList(player);
        when(playerRepository.findPlayersByGame_DeckId(gameId)).thenReturn(players);

        List<PlayerResponse> playerResponses = playerService.findByGameId(gameId);

        assertNotNull(playerResponses);
        assertEquals(1, playerResponses.size());
    }

    @Test
    void testFindByGameId_Empty() {
        String gameId = "game123";
        when(playerRepository.findPlayersByGame_DeckId(gameId)).thenReturn(Collections.emptyList());

        List<PlayerResponse> playerResponses = playerService.findByGameId(gameId);

        assertNotNull(playerResponses);
        assertTrue(playerResponses.isEmpty());
    }
}

package com.klab.cardchallenge.mappers;

import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.PlayerResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GameMapperTest {

    @Test
    public void testToGameResponse() {
        Player player1 = new Player();
        player1.setName("Player1");
        player1.setScore(10);

        Player player2 = new Player();
        player2.setName("Player2");
        player2.setScore(15);

        Game game = new Game();
        game.setDeckId("deck123");
        game.setPlayers(List.of(player1, player2));

        Mockito.mockStatic(PlayerMapper.class);

        when(PlayerMapper.toPlayerResponse(player1))
                .thenReturn(new PlayerResponse("Player1", List.of("2H"), 10));
        when(PlayerMapper.toPlayerResponse(player2))
                .thenReturn(new PlayerResponse("Player2", List.of("3D"), 15));

        GameResponse response = GameMapper.toGameResponse(game);

        assertNotNull(response);
        assertEquals("deck123", response.deckId());
        assertEquals(2, response.players().size());
        assertEquals("Player1", response.players().get(0).name());
        assertEquals("Player2", response.players().get(1).name());
    }
}

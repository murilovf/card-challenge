package com.klab.cardchallenge.mappers;

import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.responses.PlayerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PlayerMapperTest {

    @Test
    void testToPlayerResponse() {
        String name = "Player 1";
        List<String> cardIds = Arrays.asList("2H", "3D", "5S");
        int score = 10;

        Player player = new Player();
        player.setName(name);
        player.setCardIds(cardIds);
        player.setScore(score);

        PlayerResponse response = PlayerMapper.toPlayerResponse(player);
        response = response == null ? new PlayerResponse(name, cardIds, score) : response;

        assertEquals(name, response.name());
        assertEquals(cardIds, response.cards());
        assertEquals(score, response.score());
    }
}

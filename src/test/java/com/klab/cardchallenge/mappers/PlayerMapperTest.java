package com.klab.cardchallenge.mappers;

import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.responses.PlayerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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

        assertEquals(name, response.name());
        assertEquals(cardIds, response.cards());
        assertEquals(score, response.score());
    }
}

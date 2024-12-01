package com.klab.cardchallenge.controllers;

import com.klab.cardchallenge.requests.PlayerRequest;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.PlayerResponse;
import com.klab.cardchallenge.services.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlayerControllerTest {

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPlayerInGame() {
        PlayerRequest playerRequest = mock(PlayerRequest.class);
        GameResponse expectedResponse = mock(GameResponse.class);
        when(playerService.addPlayerInGame(any(PlayerRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<GameResponse> response = playerController.addPlayerInGame(playerRequest);

        assertEquals(expectedResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testListGamesCreated() {
        String gameId = "123";
        List<PlayerResponse> expectedResponse = new ArrayList<>();
        expectedResponse.add(mock(PlayerResponse.class));
        when(playerService.findByGameId(eq(gameId))).thenReturn(expectedResponse);

        ResponseEntity<List<PlayerResponse>> response = playerController.listGamesCreated(gameId);

        assertEquals(expectedResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}

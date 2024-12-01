package com.klab.cardchallenge.controllers;

import com.klab.cardchallenge.requests.GameRequest;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGame() {
        GameRequest gameRequest = mock(GameRequest.class);
        GameResponse expectedResponse = mock(GameResponse.class);
        when(gameService.create(any(GameRequest.class))).thenReturn(expectedResponse);

        ResponseEntity<GameResponse> response = gameController.createGame(gameRequest);

        assertEquals(expectedResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testFinishGame() {
        String gameId = "123";
        GameResponse expectedResponse = mock(GameResponse.class);
        when(gameService.finish(eq(gameId))).thenReturn(expectedResponse);

        ResponseEntity<GameResponse> response = gameController.finishGame(gameId);

        assertEquals(expectedResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testListGamesCreated() {
        String gameId = "123";
        GameResponse expectedResponse = mock(GameResponse.class);
        when(gameService.findById(eq(gameId))).thenReturn(expectedResponse);

        ResponseEntity<GameResponse> response = gameController.listGamesCreated(gameId);

        assertEquals(expectedResponse, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
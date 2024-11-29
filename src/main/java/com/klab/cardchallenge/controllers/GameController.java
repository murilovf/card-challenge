package com.klab.cardchallenge.controllers;

import com.klab.cardchallenge.requests.GameRequest;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok().body(gameService.create(gameRequest));
    }

    @PostMapping("/{gameId}/finish")
    public ResponseEntity<GameResponse> finishGame(@PathVariable String gameId) {
        return ResponseEntity.ok().body(gameService.finish(gameId));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> listGamesCreated(@PathVariable String gameId){
        return ResponseEntity.ok().body(gameService.findById(gameId));
    }
}

package com.klab.cardchallenge.controllers;

import com.klab.cardchallenge.requests.PlayerRequest;
import com.klab.cardchallenge.responses.GameResponse;
import com.klab.cardchallenge.responses.PlayerResponse;
import com.klab.cardchallenge.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/create")
    public ResponseEntity<GameResponse> addPlayerInGame(@RequestBody PlayerRequest playerRequest) {
        return ResponseEntity.ok().body(playerService.addPlayerInGame(playerRequest));
    }

    @GetMapping("/list/{gameId}")
    public ResponseEntity<List<PlayerResponse>> listGamesCreated(@PathVariable String gameId){
        return ResponseEntity.ok().body(playerService.findByGameId(gameId));
    }
}

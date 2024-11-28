package com.klab.cardchallenge.controllers;

import com.klab.cardchallenge.dto.GameDTO;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody GameDTO gameRequest) {
        return ResponseEntity.ok().body(gameService.create(gameRequest));
    }
}

package com.klab.cardchallenge.controllers;

import com.klab.cardchallenge.dto.GameDTO;
import com.klab.cardchallenge.dto.PlayerDTO;
import com.klab.cardchallenge.entities.Game;
import com.klab.cardchallenge.entities.Player;
import com.klab.cardchallenge.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/create")
    public ResponseEntity<Player> addPlayerInGame(@RequestBody PlayerDTO playerRequest) {
        return ResponseEntity.ok().body(playerService.addPlayerInGame(playerRequest));
    }
}

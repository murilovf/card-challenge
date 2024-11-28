package com.klab.cardchallenge.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @Column(name = "deck_id", nullable = false)
    private String deckId;

    @Column(name = "number_players", nullable = false)
    private Integer numberOfPlayers;

    @Column(name = "cards_per_hand", nullable = false)
    private Integer cardsPerHand;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime creationDate;

    public Game(String deckId, Integer numberOfPlayers, Integer cardsPerHand, List<Player> players, LocalDateTime creationDate) {
        this.deckId = deckId;
        this.numberOfPlayers = numberOfPlayers;
        this.cardsPerHand = cardsPerHand;
        this.players = players;
        this.creationDate = creationDate;
    }

    public Game() {}
}

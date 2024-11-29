package com.klab.cardchallenge.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GameWinners> gameWinners = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "finished_at")
    private LocalDateTime finishDate;

    public Game(String deckId, Integer numberOfPlayers, Integer cardsPerHand, List<Player> players, LocalDateTime creationDate) {
        this.deckId = deckId;
        this.numberOfPlayers = numberOfPlayers;
        this.cardsPerHand = cardsPerHand;
        this.players = players;
        this.creationDate = creationDate;
    }

    public Game() {}

    public Game(String deckId) {
        this.deckId = deckId;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setGameWinners(List<GameWinners> gameWinners) {
        this.gameWinners = gameWinners;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Integer getCardsPerHand() {
        return cardsPerHand;
    }

    public void setCardsPerHand(Integer cardsPerHand) {
        this.cardsPerHand = cardsPerHand;
    }

    public List<GameWinners> getGameWinners() {
        return gameWinners;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }
}

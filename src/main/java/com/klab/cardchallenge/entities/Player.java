package com.klab.cardchallenge.entities;

import com.klab.cardchallenge.utils.ListUtils;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ElementCollection
    @CollectionTable(
            name = "player_cards",
            joinColumns = @JoinColumn(name = "player_id")
    )
    @Column(name = "card_id")
    private List<String> cardIds;

    @Column(name = "score", nullable = false)
    private Integer score;

    public Player(Long id, String name, List<String> cardIds, Game game, Integer score) {
        this.id = id;
        this.name = name;
        this.cardIds = cardIds;
        this.game = game;
        this.score = score;
    }

    public Player() { }
}

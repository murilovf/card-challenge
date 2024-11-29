package com.klab.cardchallenge.entities;

import com.klab.cardchallenge.utils.CardMapper;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "player_cards",
            joinColumns = @JoinColumn(name = "player_id")
    )
    @Column(name = "card_id")
    private List<String> cardIds;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Transient
    private List<Card> cards;

    public Player(Long id, String name, List<String> cardIds, Game game, Integer score) {
        this.id = id;
        this.name = name;
        this.cardIds = cardIds;
        this.game = game;
        this.score = score;
    }

    public Player() { }

    public List<Card> getCards() {
        if (cards == null) {
            cards = cardIds.stream()
                    .map(cardId -> CardMapper.toCard(cardId))
                    .collect(Collectors.toList());
        }
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Integer getScore() {
        return getCards().stream()
                .mapToInt(card -> card.getValue().getScore())
                .sum();
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<String> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

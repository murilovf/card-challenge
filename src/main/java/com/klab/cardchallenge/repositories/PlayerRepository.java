package com.klab.cardchallenge.repositories;

import com.klab.cardchallenge.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findPlayersByGame_DeckId(String gameId);
}

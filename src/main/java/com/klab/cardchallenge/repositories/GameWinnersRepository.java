package com.klab.cardchallenge.repositories;

import com.klab.cardchallenge.entities.GameWinners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameWinnersRepository extends JpaRepository<GameWinners, Integer> {

    List<GameWinners> findGameWinnersByGame_DeckId(String gameId);

}

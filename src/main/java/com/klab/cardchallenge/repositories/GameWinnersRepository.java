package com.klab.cardchallenge.repositories;

import com.klab.cardchallenge.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}

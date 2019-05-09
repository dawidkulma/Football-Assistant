package com.football.assistant.repository;

import com.football.assistant.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League,Integer> {
}

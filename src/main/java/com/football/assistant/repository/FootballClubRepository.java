package com.football.assistant.repository;

import com.football.assistant.domain.FootballClub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballClubRepository extends JpaRepository<FootballClub, Integer> {

}

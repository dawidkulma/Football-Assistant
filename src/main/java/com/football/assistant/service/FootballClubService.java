package com.football.assistant.service;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.repository.FootballClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FootballClubService {

    private FootballClubRepository footballClubRepository;

    @Autowired
    public FootballClubService(FootballClubRepository footballClubRepository) {
        this.footballClubRepository = footballClubRepository;
    }

    public Iterable<FootballClub> lookup() {
        return footballClubRepository.findAll();
    }

    public long total() {
        return footballClubRepository.count();
    }

}
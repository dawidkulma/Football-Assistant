package com.football.assistant.service;

import com.football.assistant.api.ApiConsumer;
import com.football.assistant.api.ApiManager;
import com.football.assistant.domain.League;
import com.football.assistant.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueService extends ApiConsumer {

    private LeagueRepository leagueRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, ApiManager apiManager) {
        super(apiManager);
        this.leagueRepository = leagueRepository;
    }

    public Iterable<League> lookup() {
        return leagueRepository.findAll();
    }

    public long total() {
        return leagueRepository.count();
    }

}

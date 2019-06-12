package com.football.assistant.service;

import com.football.assistant.api.ApiConsumer;
import com.football.assistant.api.ApiManager;
import com.football.assistant.domain.FootballClub;
import com.football.assistant.domain.League;
import com.football.assistant.domain.Player;
import com.football.assistant.repository.FootballClubRepository;
import com.football.assistant.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LeagueService extends ApiConsumer {

    private LeagueRepository leagueRepository;

    private FootballClubRepository footballClubRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository, ApiManager apiManager, FootballClubRepository footballClubRepository) {
        super(apiManager);
        this.leagueRepository = leagueRepository;
        this.footballClubRepository = footballClubRepository;
    }

    public Iterable<League> lookup() {
        return leagueRepository.findAll();
    }

    public long total() {
        return leagueRepository.count();
    }

    public League findByApiId(Integer apiId) {
        League league = leagueRepository.findByApiId(apiId);
        if(league == null) {
            return fetchFromApiAndPersist(apiId);
        }
        Date lastRefreshDate = new Date(league.getLastRefreshTimestamp().getTime());
        Date currentDate = new Date();
        long differenceInMilliseconds = currentDate.getTime() - lastRefreshDate.getTime();
        if (TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS) > refreshPeriodInDays) {
            return fetchFromApiAndPersist(apiId);
        }
        return league;
    }

    private League fetchFromApiAndPersist(Integer apiId) {
        League leagueFromApi = apiManager.fetchLeagueById(apiId);
        if (leagueFromApi == null) return null;
        List<FootballClub> clubs = apiManager.fetchAllClubsInLeague(apiId);
        for(FootballClub club : clubs) {
            FootballClub footballClubInDB = this.footballClubRepository.findByApiId(club.getApiId());
            if (footballClubInDB == null) {
                leagueFromApi.addClub(club);
            } else {
                footballClubInDB.setLeague(leagueFromApi);
            }
        }
        League leagueinDB = leagueRepository.findByApiId(apiId);
        if(leagueinDB != null) {
            leagueRepository.delete(leagueinDB);
        }
        leagueRepository.save(leagueFromApi);
        return leagueFromApi;
    }

}

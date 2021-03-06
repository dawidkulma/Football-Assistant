package com.football.assistant.service;

import com.football.assistant.api.ApiConsumer;
import com.football.assistant.api.ApiManager;
import com.football.assistant.domain.FootballClub;
import com.football.assistant.domain.League;
import com.football.assistant.domain.Player;
import com.football.assistant.repository.FootballClubRepository;
import com.football.assistant.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FootballClubService extends ApiConsumer {

    private FootballClubRepository footballClubRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public FootballClubService(FootballClubRepository footballClubRepository, ApiManager apiManager, PlayerRepository playerRepository) {
        super(apiManager);
        this.footballClubRepository = footballClubRepository;
        this.playerRepository = playerRepository;
    }

    public Iterable<FootballClub> lookup() {
        return footballClubRepository.findAll();
    }

    public long total() {
        return footballClubRepository.count();
    }

    public FootballClub findByApiId(Integer apiId) {
        FootballClub footballClub = footballClubRepository.findByApiId(apiId);
        if(footballClub == null) {
            return fetchFromApiAndPersist(apiId);
        }
        Date lastRefreshDate = new Date(footballClub.getLastRefreshTimestamp().getTime());
        Date currentDate = new Date();
        long differenceInMilliseconds = currentDate.getTime() - lastRefreshDate.getTime();
        if (TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS) > refreshPeriodInDays) {
            return fetchFromApiAndPersist(apiId);
        }
        if (footballClub.getPlayers().size() < 11) {
            return fetchFromApiAndPersist(apiId);
        }
        return footballClub;
    }

    private FootballClub fetchFromApiAndPersist(Integer apiId) {
        FootballClub clubFromApi = apiManager.fetchFootballClubById(apiId);
        if (clubFromApi == null) return null;
        List<Player> players = apiManager.fetchAllPlayersInClub(apiId);
        for(Player player: players) {
            Player playerInDB = this.playerRepository.findByApiId(player.getApiId());
            if (playerInDB == null) {
                clubFromApi.addPlayer(player);
            } else {
                playerInDB.setClub(clubFromApi);
            }
        }
        FootballClub clubInDB = footballClubRepository.findByApiId(apiId);
        if(clubInDB != null) {
            clubFromApi.setLeague(clubInDB.getLeague());
            footballClubRepository.save(clubFromApi);
            footballClubRepository.delete(clubInDB);
        } else {
            footballClubRepository.save(clubFromApi);
        }
        //footballClubRepository.save(clubFromApi);
        return clubFromApi;
    }

}
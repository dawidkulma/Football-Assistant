package com.football.assistant.service;

import com.football.assistant.api.ApiConsumer;
import com.football.assistant.api.ApiManager;
import com.football.assistant.domain.FootballClub;
import com.football.assistant.domain.Player;
import com.football.assistant.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class PlayerService extends ApiConsumer {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, ApiManager apiManager) {
        super(apiManager);
        this.playerRepository = playerRepository;
    }

    public Iterable<Player> lookup() {
        return playerRepository.findAll();
    }

    public long total() {
        return playerRepository.count();
    }

    public Player findByApiId(Integer apiId) {
        Player player = playerRepository.findByApiId(apiId);
        if(player == null) {
            return fetchFromApiAndPersist(apiId);
        }
        Date lastRefreshDate = new Date(player.getLastRefreshTimestamp().getTime());
        Date currentDate = new Date();
        long differenceInMilliseconds = currentDate.getTime() - lastRefreshDate.getTime();
        if (TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS) > refreshPeriodInDays) {
            return fetchFromApiAndPersist(apiId);
        }
        return player;
    }

    private Player fetchFromApiAndPersist(Integer apiId) {
        Player playerFromApi = apiManager.fetchPlayerById(apiId);
        if (playerFromApi == null) return null;
        playerRepository.save(playerFromApi);
        return playerFromApi;
    }

}

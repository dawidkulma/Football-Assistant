package com.football.assistant.service;

import com.football.assistant.api.ApiConsumer;
import com.football.assistant.api.ApiManager;
import com.football.assistant.domain.Player;
import com.football.assistant.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

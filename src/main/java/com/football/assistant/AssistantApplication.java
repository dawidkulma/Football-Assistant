package com.football.assistant;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.repository.FootballClubRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssistantApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(FootballClubRepository footballClubRepository) {
		return args -> {
			footballClubRepository.save( new FootballClub("Arsenal", "ARS"));
		};
	}

}

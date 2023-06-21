package com.soccer.matchUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SoccerMatchUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccerMatchUpApplication.class, args);
	}

}

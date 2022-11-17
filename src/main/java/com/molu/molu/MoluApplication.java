package com.molu.molu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MoluApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoluApplication.class, args);
	}

}

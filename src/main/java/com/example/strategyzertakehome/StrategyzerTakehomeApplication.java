package com.example.strategyzertakehome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class StrategyzerTakehomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrategyzerTakehomeApplication.class, args);
	}

}

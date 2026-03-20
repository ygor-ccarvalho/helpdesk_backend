package com.ygor.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ygor.helpdesk.services.DBService;

@Configuration
@Profile("test")

public class TestConfig {

	
	@Autowired
	
	private DBService dbService;
	
	
	@Bean
	void instanciaDB() {
		this.dbService.instanciaDB();
	}
	
	
}

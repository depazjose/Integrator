package com.example.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class IntegratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegratorApplication.class, args);
	}

}

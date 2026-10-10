package com.example;

import org.springframework.boot.SpringApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@SpringBootApplication
public class ComplaintportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComplaintportalApplication.class, args);
	}

}

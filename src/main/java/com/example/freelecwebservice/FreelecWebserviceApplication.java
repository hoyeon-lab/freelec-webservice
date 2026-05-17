package com.example.freelecwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FreelecWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelecWebserviceApplication.class, args);
	}

}

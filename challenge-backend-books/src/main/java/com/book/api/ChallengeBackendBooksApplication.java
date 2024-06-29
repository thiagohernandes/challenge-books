package com.book.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.book.api")
public class ChallengeBackendBooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeBackendBooksApplication.class, args);
	}

}

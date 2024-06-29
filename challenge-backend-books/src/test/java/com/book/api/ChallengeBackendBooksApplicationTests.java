package com.book.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChallengeBackendBooksApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> ChallengeBackendBooksApplication.main(new String[0]));
	}

}

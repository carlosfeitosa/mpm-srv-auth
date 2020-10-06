package com.skull.auth.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthUserTest {

	private static final String AUTH_USERNAME = "teste";
	private static final String AUTH_EMAIL = "teste@teste.com";
	private static final String AUTH_PASSWORD = "password";

	private AuthUser user;

	@BeforeEach
	void setupTest() {

		user = new AuthUser();
	}

	@DisplayName("Test if to .toString() has all expected object attributes")
	@Test
	void testIfToStringHasAllExpectedObjectAttributes() {

		user.setName(AUTH_USERNAME);
		user.setEmailId(AUTH_EMAIL);
		user.setPassword(AUTH_PASSWORD);

		String userString = user.toString();

		assertThat(userString).contains(AUTH_USERNAME).contains(AUTH_EMAIL).contains(AUTH_PASSWORD);
	}

}

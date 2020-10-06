package com.skull.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest(properties = { "service.preload.database=true" })
class CustomUserDetailsServiceTest {

	private static final String USER_EMAIL = "carlos.feitosa.nt@gmail.com";

	private static final String USERNAME_NOT_FOUND_EXCEPTION_TEST = "was not found in the database";

	@Autowired
	CustomUserDetailsService service;

	@Test
	@DisplayName("Test if can find user by it's e-mail address")
	void testIfCanFindserByEmail() {

		UserDetails userDetails = service.loadUserByUsername(USER_EMAIL);

		assertThat(userDetails).isNotNull();

		assertThat(userDetails.getUsername()).isEqualTo(USER_EMAIL);
	}

	@Test
	@DisplayName("Test if cant find user by it's wrong e-mail address")
	void testIfCanFindserByEmailWrongEmail() {

		String wrongUsername = USER_EMAIL.concat("X");

		Exception exception = assertThrows(UsernameNotFoundException.class, () -> {

			service.loadUserByUsername(wrongUsername);
		});

		String actualMessage = exception.getMessage();

		assertThat(actualMessage).contains(USERNAME_NOT_FOUND_EXCEPTION_TEST);
	}

}

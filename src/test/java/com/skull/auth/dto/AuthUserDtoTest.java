package com.skull.auth.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.skull.auth.model.AuthUser;

class AuthUserDtoTest {

	private static final String AUTH_USERNAME = "teste";
	private static final String AUTH_EMAIL = "teste@teste.com";
	private static final String AUTH_PASSWORD = "password";

	@Test
	@DisplayName("Test if can perform equals and hashcode")
	void testIfCanPerformEqualsAndHashCode() {

		AuthUser authUser = new AuthUser();
		authUser.setName(AUTH_USERNAME);
		authUser.setEmailId(AUTH_EMAIL);
		authUser.setPassword(AUTH_PASSWORD);

		AuthUserDto user1 = new AuthUserDto(authUser, new ArrayList<>());
		AuthUserDto user2 = new AuthUserDto(authUser, new ArrayList<>());

		assertThat(user1).isEqualTo(user2).hasSameHashCodeAs(user2);
	}

	@Test
	@DisplayName("Test if can perform toString()")
	void testIfCanPerformToString() {

		AuthUser authUser = new AuthUser();
		authUser.setName(AUTH_USERNAME);
		authUser.setEmailId(AUTH_EMAIL);
		authUser.setPassword(AUTH_PASSWORD);

		AuthUserDto user = new AuthUserDto(authUser, new ArrayList<>());

		String newId = UUID.randomUUID().toString();
		String newName = authUser.getName().concat(authUser.getName());

		user.setId(newId);
		user.setName(newName);

		String userString = user.toString();

		assertThat(userString).contains(newId).contains(newName);
	}

}

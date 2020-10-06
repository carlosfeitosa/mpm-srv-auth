package com.skull.auth.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthRoleTest {

	private static final String ROLE_NAME = "teste";

	private AuthRole role;

	@BeforeEach
	void setupTest() {

		role = new AuthRole();
	}

	@DisplayName("Test if to .toString() has all expected object attributes")
	@Test
	void testIfToStringHasAllExpectedObjectAttributes() {

		role.setName(ROLE_NAME);
		role.setLinkedPermissions(null);

		String roleString = role.toString();

		assertThat(roleString).contains(ROLE_NAME);
	}

	@DisplayName("Test if to .toString() has all expected object attributes (constructor)")
	@Test
	void testIfToStringHasAllExpectedObjectAttributesByConstructor() {

		role = new AuthRole(ROLE_NAME);
		role.setLinkedPermissions(null);

		String roleString = role.toString();

		assertThat(roleString).contains(ROLE_NAME);
	}

}

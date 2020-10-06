package com.skull.auth.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthPermissionTest {

	private static final String PERMISSION_NAME = "teste";

	private AuthPermission permission;

	@BeforeEach
	void setupTest() {

		permission = new AuthPermission();
	}

	@DisplayName("Test if to .toString() has all expected object attributes")
	@Test
	void testIfToStringHasAllExpectedObjectAttributes() {

		permission.setName(PERMISSION_NAME);
		permission.setLinkedRoles(null);

		String permissionString = permission.toString();

		assertThat(permissionString).contains(PERMISSION_NAME);
	}

	@DisplayName("Test if to .toString() has all expected object attributes (constructor)")
	@Test
	void testIfToStringHasAllExpectedObjectAttributesByConstructor() {

		permission = new AuthPermission(PERMISSION_NAME);
		permission.setLinkedRoles(null);

		String permissionString = permission.toString();

		assertThat(permissionString).contains(PERMISSION_NAME);
	}

}

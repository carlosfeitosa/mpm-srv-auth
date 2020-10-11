package com.skull.auth.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import com.skull.auth.model.AuthPermission;
import com.skull.auth.model.AuthRole;

class AuthRoleConverterTest {

	private static final String ROLE_NAME = "ROLE_#%d";
	private static final String PERMISSION_NAME = "PERMISSION_#%d";

	private static AuthRoleConverter converter;

	@BeforeAll
	public static void setupBeforeTest() {

		converter = new AuthRoleConverter();
	}

	@DisplayName("Test if converter can convert a list from entity to dto")
	@Test
	void testIfConverterCanConvertAEntityListToDtoList() {

		List<AuthRole> roleList = new ArrayList<>();
		List<AuthPermission> permissionList = new ArrayList<>();
		List<String> permissionStringList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			String permissionName = String.format(PERMISSION_NAME, i);

			permissionList.add(new AuthPermission(permissionName));
			permissionStringList.add(permissionName);
		}

		for (int i = 0; i < 10; i++) {

			String roleName = String.format(ROLE_NAME, i);

			AuthRole role = new AuthRole(roleName);
			role.setLinkedPermissions(permissionList);

			roleList.add(role);
		}

		List<GrantedAuthority> authorityList = converter.convertFromRoleListToPermissionList(roleList);

		assertThat(authorityList.size()).isEqualTo(roleList.size() * permissionList.size());

		for (GrantedAuthority authority : authorityList) {

			assertThat(permissionStringList).contains(authority.getAuthority());
		}
	}

}

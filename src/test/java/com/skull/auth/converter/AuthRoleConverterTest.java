package com.skull.auth.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import com.skull.auth.model.AuthRole;

class AuthRoleConverterTest {

	private static final String ROLE_NAME = "ROLE_#%d";

	private static AuthRoleConverter CONVERTER;

	@BeforeAll
	public static void setupBeforeTest() {

		CONVERTER = new AuthRoleConverter();
	}

	@DisplayName("Test if converter can convert a list from entity to dto")
	@Test
	void testIfConverterCanConvertAEntityListToDtoList() {

		List<AuthRole> roleList = new ArrayList<>();
		List<String> roleStringList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {

			String roleName = String.format(ROLE_NAME, i);

			roleList.add(new AuthRole(roleName));
			roleStringList.add(roleName);
		}

		List<GrantedAuthority> authorityList = CONVERTER.convertFromRoleList(roleList);

		assertThat(authorityList.size()).isEqualTo(roleList.size());

		for (GrantedAuthority authority : authorityList) {

			assertThat(roleStringList).contains(authority.getAuthority());
		}
	}

}

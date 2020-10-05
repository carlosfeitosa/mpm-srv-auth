package com.skull.auth.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.skull.auth.model.AuthRole;

import lombok.extern.slf4j.Slf4j;

/**
 * Converter for auth role.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-05
 *
 */
@Component
@Slf4j
public class AuthRoleConverter {

	public List<GrantedAuthority> convertFromRoleList(List<AuthRole> roles) {

		List<GrantedAuthority> result = new ArrayList<>();

		log.info("Converting from Role list to GranthedAuthority list");

		for (AuthRole role : roles) {

			log.debug("Recuperando role {}", role.getName());

			result.add(new SimpleGrantedAuthority(role.getName()));
		}

		return result;
	}
}

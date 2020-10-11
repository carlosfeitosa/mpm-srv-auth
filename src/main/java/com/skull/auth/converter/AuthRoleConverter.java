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
public class AuthRoleConverter { // NOPMD by skull on 10/11/20, 12:32 AM

	/**
	 * Convert a AuthRole list to GrantedAuthority list.
	 * 
	 * @param roles AuthRole entity
	 * 
	 * @return GrantedAuthority converted list
	 */
	public List<GrantedAuthority> convertFromRoleList(final List<AuthRole> roles) {

		final List<GrantedAuthority> result = new ArrayList<>();

		log.info("Converting from Role list to GranthedAuthority list");

		for (final AuthRole role : roles) {

			log.debug("Recuperando role {}", role.getName());

			result.add(new SimpleGrantedAuthority(role.getName())); // NOPMD by skull on 10/11/20, 12:35 AM
		}

		return result;
	}
}

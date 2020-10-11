package com.skull.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.skull.auth.converter.AuthRoleConverter;
import com.skull.auth.dto.AuthUserDto;
import com.skull.auth.model.AuthUser;
import com.skull.auth.repository.AuthUserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for auth user entity.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService { // NOPMD by skull on 10/11/20, 9:07 AM

	/**
	 * Message for user not found exception.
	 */
	private static final String NOT_FOUND_MSG = "User \"%s\" was not found in the database";

	/**
	 * User repository.
	 */
	@Autowired
	private AuthUserRepository repo; // NOPMD by skull on 10/11/20, 9:14 AM

	/**
	 * Role converter.
	 */
	@Autowired
	private AuthRoleConverter authRoleConverter; // NOPMD by skull on 10/11/20, 9:14 AM

	@Override
	public UserDetails loadUserByUsername(final String username) {

		log.info("Loading user");
		log.debug("Username: {}", username);

		AuthUser user;

		user = repo.findByEmailId(username);

		if (null == user) {

			log.info("User not found");

			throw new UsernameNotFoundException(String.format(NOT_FOUND_MSG, username));
		} else {

			log.info("User found");

			return new AuthUserDto(user, authRoleConverter.convertFromRoleList(user.getLinkedRoles()));
		}
	}
}

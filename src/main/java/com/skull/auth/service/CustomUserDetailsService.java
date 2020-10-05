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

/**
 * Service for auth user entity.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	/**
	 * Message for user not found exception.
	 */
	private static final String USER_NOT_FOUND_MSG = "User \"%s\" was not found in the database";

	/**
	 * User repository.
	 */
	@Autowired
	AuthUserRepository repo;

	@Autowired
	AuthRoleConverter authRoleConverter;

	@Override
	public UserDetails loadUserByUsername(String username) {

		AuthUser user = null;

		try {

			user = repo.findByEmailId(username);

			if (user != null && user.getId() != null && !"".equalsIgnoreCase(user.getId().toString())) {

				return new AuthUserDto(user, authRoleConverter.convertFromRoleList(user.getLinkedRoles()));
			} else {

				throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
			}
		} catch (Exception e) {

			throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
		}
	}
}

package com.skull.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
	 * User repository.
	 */
	@Autowired
	AuthUserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		AuthUser user = null;

		try {

			user = repo.findByEmailId(username);

			if (user != null && user.getId() != null && !"".equalsIgnoreCase(user.getId().toString())) {

				return new AuthUserDto(user);
			} else {

				throw new UsernameNotFoundException("User " + username + " was not found in the database");
			}
		} catch (Exception e) {

			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

	}

}

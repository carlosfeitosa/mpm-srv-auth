package com.skull.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skull.auth.model.AuthUser;

/**
 * Data repository for auth's user.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-09-04
 *
 */
@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {

	/**
	 * Find users by email.
	 * 
	 * @param emailId user's e-mail
	 * 
	 * @return entity
	 */
	AuthUser findByEmailId(String emailId);
}

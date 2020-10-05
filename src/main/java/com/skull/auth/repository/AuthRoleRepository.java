package com.skull.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skull.auth.model.AuthRole;

/**
 * Repository for roles.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@Repository
public interface AuthRoleRepository extends JpaRepository<AuthRole, UUID> {

}

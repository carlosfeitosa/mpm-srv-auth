package com.skull.auth.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.skull.auth.model.AuthRole;
import com.skull.auth.model.AuthUser;
import com.skull.auth.repository.AuthRoleRepository;
import com.skull.auth.repository.AuthUserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Loads mocked users.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@Configuration
@Slf4j
public class LoadDatabase { // NOPMD by skull on 8/8/20, 7:07 PM

	/**
	 * Password encoder.
	 */
	@Autowired
	PasswordEncoder encoder;

	/**
	 * Mocker master user name.
	 */
	private static final String MOCKED_MASTER_NAME = "Carlos Feitosa";

	/**
	 * Mocker master user email.
	 */
	private static final String MOCKED_MASTER_EMAIL = "carlos.feitosa.nt@gmail.com";

	/**
	 * Mocker master user password.
	 */
	private static final String MOCKED_MASTER_PASSWORD = "s3cr3t";

	/**
	 * Root role name.
	 */
	private static final String ROLE_ROOT = "ROLE_ROOT";

	/**
	 * Admin role name.
	 */
	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	/**
	 * Project manager role name.
	 */
	private static final String ROLE_PM = "ROLE_PM";

	/**
	 * PMO role name.
	 */
	private static final String ROLE_PMO = "ROLE_PMO";

	/**
	 * DevTeam role name.
	 */
	private static final String ROLE_DEVTEAM = "ROLE_DEVTEAM";

	/**
	 * Init database with mock data.
	 * 
	 * @param repository   repository used to save information.
	 * @param initMockedDb true to mock data into database
	 * 
	 * @return args
	 */
	@Bean
	public CommandLineRunner initDatabaseUser(final AuthUserRepository repository,
			final @Value("${service.preload.database}") boolean initMockedDb) {
		return args -> {

			if (initMockedDb) {

				log.info("Preloading database (users)...");

				AuthUser master = getMockedMasterUser();

				repository.save(master);
			}
		};
	}

	@Bean
	public CommandLineRunner initDatabaseRole(final AuthRoleRepository repository,
			final @Value("${service.preload.database}") boolean initMockedDb) {
		return args -> {

			if (initMockedDb) {

				log.info("Preloading database (role)...");

				for (AuthRole role : getMockedRoleList()) {

					log.debug("Saving \"{}\" role", role.getName());

					repository.save(role);
				}
			}
		};
	}

	/**
	 * Returns a mocked role list.
	 * 
	 * @return mocked role list
	 */
	private List<AuthRole> getMockedRoleList() {

		List<AuthRole> result = new ArrayList<>();

		result.add(new AuthRole(ROLE_ROOT));
		result.add(new AuthRole(ROLE_ADMIN));
		result.add(new AuthRole(ROLE_PM));
		result.add(new AuthRole(ROLE_PMO));
		result.add(new AuthRole(ROLE_DEVTEAM));

		return result;
	}

	/**
	 * Returns a mocked master user.
	 * 
	 * @return mocked master user
	 */
	private AuthUser getMockedMasterUser() {

		AuthUser result = new AuthUser();

		result.setName(MOCKED_MASTER_NAME);
		result.setEmailId(MOCKED_MASTER_EMAIL);
		result.setPassword(encoder.encode(MOCKED_MASTER_PASSWORD));

		return result;
	}

}

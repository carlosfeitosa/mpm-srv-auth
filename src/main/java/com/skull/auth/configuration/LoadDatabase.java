package com.skull.auth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.skull.auth.model.AuthUser;
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
	 * Init database with mock data.
	 * 
	 * @param repository   repository used to save information.
	 * @param initMockedDb true to mock data into database
	 * 
	 * @return args
	 */
	@Bean
	public CommandLineRunner initDatabase(final AuthUserRepository repository,
			final @Value("${service.preload.database}") boolean initMockedDb) {
		return args -> {

			if (initMockedDb) {

				log.info("Preloading database...");

				AuthUser master = getMockedMasterUser();

				repository.save(master);
			}
		};
	}

	/**
	 * Returns a mocker master user.
	 * 
	 * @return mocked master user
	 */
	private AuthUser getMockedMasterUser() {

		AuthUser result = new AuthUser();

		result.setName(MOCKED_MASTER_NAME);
		result.setEmailId(MOCKED_MASTER_EMAIL);
		result.setPassword(MOCKED_MASTER_PASSWORD);

		return result;
	}

}

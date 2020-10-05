package com.skull.auth.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.skull.auth.model.AuthPermission;
import com.skull.auth.model.AuthRole;
import com.skull.auth.model.AuthUser;
import com.skull.auth.repository.AuthPermissionRepository;
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
	 * Role repository.
	 */
	@Autowired
	AuthRoleRepository roleRepo;

	/**
	 * Permission repository.
	 */
	@Autowired
	AuthPermissionRepository permissionRepo;

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
	 * Create project permission.
	 */
	private static final String PERMISSION_CREATE_PROJECT = "PERMISSION_CREATE_PROJECT";

	/**
	 * Edit project permission.
	 */
	private static final String PERMISSION_EDIT_PROJECT = "PERMISSION_EDIT_PROJECT";

	/**
	 * Delete project permission.
	 */
	private static final String PERMISSION_DELETE_PROJECT = "PERMISSION_DELETE_PROJECT";

	/**
	 * View all projects permission.
	 */
	private static final String PERMISSION_VIEW_ALL_PROJECT = "PERMISSION_VIEW_ALL_PROJECT";

	/**
	 * View project permission.
	 */
	private static final String PERMISSION_VIEW_PROJECT = "PERMISSION_VIEW_PROJECT";

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

				log.info("Preloading database...");

				List<AuthRole> allRolesList = initRoles();

				log.info("Preloading database (users)...");

				AuthUser master = getMockedMasterUser();

				master.setLinkedRoles(allRolesList);

				repository.save(master);
			}
		};
	}

	/**
	 * Initialize roles.
	 */
	private List<AuthRole> initRoles() {

		List<AuthRole> result = getMockedRoleList();

		log.info("Preloading database (role)...");

		for (AuthRole role : result) {

			log.debug("Saving \"{}\" role", role.getName());

			roleRepo.save(role);
		}

		return result;
	}

	/**
	 * Returns a mocked role list.
	 * 
	 * @return mocked role list
	 */
	private List<AuthRole> getMockedRoleList() {

		List<AuthRole> result = new ArrayList<>();

		List<AuthPermission> projectAllPermissions = getMockedProjectAllPermissionList();

		log.info("Saving project permissions");

		for (AuthPermission permission : projectAllPermissions) {

			log.debug("Saving \"{}\" profile", permission.getName());

			permissionRepo.save(permission);
		}

		List<AuthPermission> rootPermissions = new ArrayList<>();
		List<AuthPermission> adminPermissions = new ArrayList<>();
		List<AuthPermission> pmPermissions = new ArrayList<>();
		List<AuthPermission> pmoPermissions = new ArrayList<>();
		List<AuthPermission> devteamPermissions = new ArrayList<>();

		rootPermissions.addAll(projectAllPermissions);
		adminPermissions.addAll(projectAllPermissions);
		pmPermissions.addAll(projectAllPermissions);
		pmoPermissions.addAll(projectAllPermissions);
		devteamPermissions.addAll(projectAllPermissions);

		AuthRole rootRole = new AuthRole(ROLE_ROOT);
		AuthRole adminRole = new AuthRole(ROLE_ADMIN);
		AuthRole pmRole = new AuthRole(ROLE_PM);
		AuthRole pmoRole = new AuthRole(ROLE_PMO);
		AuthRole devteamRole = new AuthRole(ROLE_DEVTEAM);

		rootRole.setLinkedPermissions(rootPermissions);
		adminRole.setLinkedPermissions(pmPermissions);
		pmRole.setLinkedPermissions(pmPermissions);
		pmoRole.setLinkedPermissions(pmoPermissions);
		devteamRole.setLinkedPermissions(devteamPermissions);

		result.add(rootRole);
		result.add(adminRole);
		result.add(pmRole);
		result.add(pmoRole);
		result.add(devteamRole);

		return result;
	}

	/**
	 * Returns a mocked permission list.
	 * 
	 * @return mocked permission list
	 */
	private List<AuthPermission> getMockedProjectAllPermissionList() {

		List<AuthPermission> result = new ArrayList<>();

		result.add(new AuthPermission(PERMISSION_CREATE_PROJECT));
		result.add(new AuthPermission(PERMISSION_EDIT_PROJECT));
		result.add(new AuthPermission(PERMISSION_DELETE_PROJECT));
		result.add(new AuthPermission(PERMISSION_VIEW_ALL_PROJECT));
		result.add(new AuthPermission(PERMISSION_VIEW_PROJECT));

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

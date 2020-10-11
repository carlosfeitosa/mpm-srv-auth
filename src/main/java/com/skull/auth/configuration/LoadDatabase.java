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
	private PasswordEncoder encoder; // NOPMD by skull on 10/10/20, 11:51 PM

	/**
	 * Role repository.
	 */
	@Autowired
	private AuthRoleRepository roleRepo; // NOPMD by skull on 10/10/20, 11:51 PM

	/**
	 * Permission repository.
	 */
	@Autowired
	private AuthPermissionRepository permissionRepo; // NOPMD by skull on 10/10/20, 11:51 PM

	/**
	 * Mocker master user name.
	 */
	private static final String MCKD_MASTER_NAME = "Carlos Feitosa";

	/**
	 * Mocker master user email.
	 */
	private static final String MCKD_MASTER_EMAIL = "carlos.feitosa.nt@gmail.com";

	/**
	 * Mocker master user password.
	 */
	private static final String MCKD_MASTER_PASS = "s3cr3t";

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
	private static final String PERM_NEW_PROJECT = "PERMISSION_NEW_PROJECT";

	/**
	 * Edit project permission.
	 */
	private static final String PERM_EDIT_PROJECT = "PERMISSION_EDIT_PROJECT";

	/**
	 * Delete project permission.
	 */
	private static final String PERM_DEL_PROJECT = "PERMISSION_DELETE_PROJECT";

	/**
	 * View all projects permission.
	 */
	private static final String PERM_VALL_PROJECT = "PERMISSION_VIEW_ALL_PROJECT";

	/**
	 * View project permission.
	 */
	private static final String PERM_VIEW_PROJECT = "PERMISSION_VIEW_PROJECT";

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

				final List<AuthRole> allRolesList = initRoles();

				log.info("Preloading database (users)...");

				final AuthUser master = getMockedMasterUser();

				master.setLinkedRoles(allRolesList);

				repository.save(master);
			}
		};
	}

	/**
	 * Initialize roles.
	 */
	private List<AuthRole> initRoles() {

		final List<AuthRole> result = getMockedRoleList();

		log.info("Preloading database (role)...");

		for (final AuthRole role : result) {

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

		final List<AuthRole> result = new ArrayList<>();

		final List<AuthPermission> projectPerms = getMockedProjectAllPermissionList();

		savePermissions(projectPerms);

		final List<AuthRole> projectRoles = getProjectMockedRoles(projectPerms);

		result.addAll(projectRoles);

		return result;
	}

	/**
	 * Return mocked project roles.
	 * 
	 * @param projectPerms project permissions
	 * 
	 * @return roles list
	 */
	private List<AuthRole> getProjectMockedRoles(final List<AuthPermission> projectPerms) {

		final List<AuthPermission> defaultPerms = new ArrayList<>(projectPerms);

		final List<AuthRole> result = new ArrayList<>();

		result.add(getProjectRole(ROLE_ROOT, defaultPerms));
		result.add(getProjectRole(ROLE_ADMIN, defaultPerms));
		result.add(getProjectRole(ROLE_PM, defaultPerms));
		result.add(getProjectRole(ROLE_PMO, defaultPerms));

		final List<AuthPermission> devteamPerms = new ArrayList<>(projectPerms);

		removeProjectUnauthorizedAccessForDevTeamPermission(devteamPerms);

		result.add(getProjectRole(ROLE_DEVTEAM, devteamPerms));

		return result;
	}

	/**
	 * Remove unauthorized project access for devteam.
	 * 
	 * @param roles default role list
	 */
	private void removeProjectUnauthorizedAccessForDevTeamPermission(final List<AuthPermission> permissions) {

		for (final AuthPermission permission : permissions) {

			if (permission.getName().equals(PERM_VALL_PROJECT)) {

				permissions.remove(permission);
			}
		}
	}

	/**
	 * Return project root role.
	 * 
	 * @param projectPerms project permissions
	 * 
	 * @return root role
	 */
	private AuthRole getProjectRole(final String roleName, final List<AuthPermission> projectPerms) {

		final AuthRole rootRole = new AuthRole(roleName);

		rootRole.setLinkedPermissions(projectPerms);

		return rootRole;
	}

	/**
	 * Save permissions.
	 * 
	 * @param permissions permissions list
	 */
	private void savePermissions(final List<AuthPermission> permissions) {

		log.info("Saving project permissions");

		for (final AuthPermission permission : permissions) {

			log.debug("Saving \"{}\" profile", permission.getName());

			permissionRepo.save(permission);
		}
	}

	/**
	 * Returns a mocked permission list.
	 * 
	 * @return mocked permission list
	 */
	private List<AuthPermission> getMockedProjectAllPermissionList() {

		final List<AuthPermission> result = new ArrayList<>();

		result.add(new AuthPermission(PERM_NEW_PROJECT));
		result.add(new AuthPermission(PERM_EDIT_PROJECT));
		result.add(new AuthPermission(PERM_DEL_PROJECT));
		result.add(new AuthPermission(PERM_VALL_PROJECT));
		result.add(new AuthPermission(PERM_VIEW_PROJECT));

		return result;
	}

	/**
	 * Returns a mocked master user.
	 * 
	 * @return mocked master user
	 */
	private AuthUser getMockedMasterUser() {

		final AuthUser result = new AuthUser();

		result.setName(MCKD_MASTER_NAME);
		result.setEmailId(MCKD_MASTER_EMAIL);
		result.setPassword(encoder.encode(MCKD_MASTER_PASS));

		return result;
	}

}

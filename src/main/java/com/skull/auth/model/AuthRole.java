package com.skull.auth.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Model for role entity.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@NoArgsConstructor
@ToString(callSuper = true)
@Entity(name = "role")
public class AuthRole {

	/**
	 * User's identifier
	 */
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	@Getter
	private UUID id; // NOPMD by skull on 10/11/20, 9:05 AM

	/**
	 * User's name.
	 */
	@NonNull
	@Column(name = "name", length = 50)
	@Getter
	@Setter
	private String name;

	/**
	 * Permissions list.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	@Getter
	@Setter
	private List<AuthPermission> linkedPermissions;

	/**
	 * User list.
	 */
	@ManyToMany(mappedBy = "linkedRoles")
	@Getter
	@Setter
	private List<AuthUser> linkedUsers;

	/**
	 * Default constructor.
	 * 
	 * @param name role name
	 */
	public AuthRole(@NonNull final String name) {

		super();

		this.name = name;
	}

}

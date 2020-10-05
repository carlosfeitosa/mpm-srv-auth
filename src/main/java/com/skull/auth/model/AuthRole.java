package com.skull.auth.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private UUID id;

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
	@ManyToMany
	@JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<AuthPermission> linkedPermissions;

	/**
	 * Default constructor.
	 * 
	 * @param name role name
	 */
	public AuthRole(@NonNull String name) {

		super();

		this.name = name;
	}

}

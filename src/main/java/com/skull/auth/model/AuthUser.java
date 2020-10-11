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
 * Entity model for user.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@NoArgsConstructor
@ToString(callSuper = true)
@Entity(name = "user")
public class AuthUser {

	/**
	 * User's identifier
	 */
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	@Getter
	private UUID id; // NOPMD by skull on 10/11/20, 9:06 AM

	/**
	 * User's name.
	 */
	@NonNull
	@Column(name = "name", length = 200)
	@Getter
	@Setter
	private String name;

	/**
	 * User's emails.
	 */
	@NonNull
	@Column(name = "email_id", length = 200, unique = true)
	@Getter
	@Setter
	private String emailId;

	/**
	 * User's password.
	 */
	@NonNull
	@Column(name = "password", length = 200)
	@Getter
	@Setter
	private String password;

	/**
	 * Roles list
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Getter
	@Setter
	private List<AuthRole> linkedRoles;
}

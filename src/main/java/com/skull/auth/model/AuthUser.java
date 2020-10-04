package com.skull.auth.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	private UUID id;

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
	 * User's authorities list.
	 */
//	@Getter
//	@Setter
//	@Transient
//	private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
}

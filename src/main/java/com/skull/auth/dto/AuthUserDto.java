package com.skull.auth.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.skull.auth.model.AuthUser;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Custom spring user.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-04
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AuthUserDto extends User {

	/**
	 * Serial number.
	 */
	private static final long serialVersionUID = 1178577249738312073L;

	/**
	 * User's id.
	 */
	private String id; // NOPMD by skull on 10/11/20, 9:01 AM

	/**
	 * User's name.
	 */
	private String name;

	/**
	 * Public constructor.
	 * 
	 * @param userEntity      user entity
	 * @param authorizedRoles authorized roles
	 */
	public AuthUserDto(final AuthUser userEntity, final List<GrantedAuthority> authorizedRoles) {

		super(userEntity.getEmailId(), userEntity.getPassword(), authorizedRoles);

		this.name = userEntity.getName();

		if (null != userEntity.getId()) {

			this.id = userEntity.getId().toString();
		}
	}
}

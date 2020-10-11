package com.skull.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.skull.auth.service.CustomUserDetailsService;

/**
 * Configuration for web security.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-03
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // NOPMD by skull on 10/11/20, 12:24 AM

	/**
	 * Custom user defails service.
	 */
	@Autowired
	private CustomUserDetailsService customUserDetailsService; // NOPMD by skull on 10/11/20, 12:25 AM

	@Override
	@Autowired
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserDetailsService).passwordEncoder(encoder()); // NOPMD by skull on 10/11/20, 12:31 AM
	}

	/**
	 * Password encoder.
	 * 
	 * @return password encoder
	 */
	@Bean
	public PasswordEncoder encoder() {

		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests().anyRequest().authenticated().and().sessionManagement() // NOPMD by skull on 10/11/20,
																						// 12:27 AM
				.sessionCreationPolicy(SessionCreationPolicy.NEVER);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

}

package com.skull.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Configuration for OAuth2.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10-03
 *
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter { // NOPMD by skull on 10/11/20, 12:09 AM

	/**
	 * Client's id.
	 */
	@Value("${security.oauth2.client.id}")
	private String clientid; // NOPMD by skull on 10/11/20, 12:09 AM

	/**
	 * Client's secret.
	 */
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret; // NOPMD by skull on 10/11/20, 12:09 AM

	/**
	 * Private key.
	 */
	@Value("${security.oauth2.privateKey}")
	private String privateKey; // NOPMD by skull on 10/11/20, 12:09 AM

	/**
	 * Public key.
	 */
	@Value("${security.oauth2.publicKey}")
	private String publicKey; // NOPMD by skull on 10/11/20, 12:09 AM

	/**
	 * Password encoder.
	 */
	@Autowired
	private PasswordEncoder passwordEncoder; // NOPMD by skull on 10/11/20, 12:09 AM

	/**
	 * Authentication bean.
	 */
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager; // NOPMD by skull on 10/11/20, 12:09 AM

	/**
	 * Default access token validity seconds.
	 */
	private static final int VALIDITY_SECS = 3600;

	/**
	 * Default refresh token validity seconds.
	 */
	private static final int REFRESH_SECS = 18_000;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {

		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()"); // NOPMD by skull on 10/11/20, 12:23 AM
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient(clientid).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write") // NOPMD by skull on 10/11/20, 12:23 AM
				.authorizedGrantTypes("client_credentials", "password", "refresh_token")
				.accessTokenValiditySeconds(VALIDITY_SECS).refreshTokenValiditySeconds(REFRESH_SECS);
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()) // NOPMD by skull on 10/11/20, 12:23 AM
				.accessTokenConverter(tokenEnhancer());
	}

	/**
	 * Token store.
	 * 
	 * @return JWT token store
	 */
	@Bean
	public JwtTokenStore tokenStore() {

		return new JwtTokenStore(tokenEnhancer());
	}

	/**
	 * Token converter.
	 * 
	 * @return JWT access token converter
	 */
	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {

		final JwtAccessTokenConverter converter = new CustomTokenEnhancer();

		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);

		return converter;
	}

}

package com.skull.auth.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.skull.auth.dto.AuthUserDto;

/**
 * Configuration for custom access token.
 * 
 * @author Carlos Feitosa (carlos.feitosa.nt@gmail.com)
 * @since 2020-10=03
 *
 */
public class CustomTokenEnhancer extends JwtAccessTokenConverter { // NOPMD by skull on 10/6/20, 6:06 PM

	@Override
	public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {

		OAuth2AccessToken resultToken = accessToken; // NOPMD by skull on 10/9/20, 12:16 AM

		if (!authentication.isClientOnly()) {

			final AuthUserDto user = (AuthUserDto) authentication.getPrincipal();
			final Map<String, Object> info = new ConcurrentHashMap<>(accessToken.getAdditionalInformation());

			updateInfo(user, info);

			final DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
			customAccessToken.setAdditionalInformation(info);

			resultToken = customAccessToken;
		}

		return super.enhance(resultToken, authentication);
	}

	/**
	 * Update user info.
	 * 
	 * @param user user dto
	 * @param info user info
	 */
	private void updateInfo(final AuthUserDto user, final Map<String, Object> info) {

		if (user.getId() != null) {

			info.put("id", user.getId());
		}

		if (user.getName() != null) {

			info.put("name", user.getName());
		}

		if (user.getUsername() != null) {

			info.put("userName", user.getUsername());
		}
	}
}

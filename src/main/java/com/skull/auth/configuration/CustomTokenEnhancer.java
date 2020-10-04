package com.skull.auth.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

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
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		if (!authentication.isClientOnly()) {

			AuthUserDto user = (AuthUserDto) authentication.getPrincipal();
			Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

			if (user.getId() != null)
				info.put("id", user.getId());

			if (user.getName() != null)
				info.put("name", user.getName());

			if (user.getUsername() != null)
				info.put("userName", user.getUsername());

			DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
			customAccessToken.setAdditionalInformation(info);

			return super.enhance(customAccessToken, authentication);
		}

		return super.enhance(accessToken, authentication);
	}
}

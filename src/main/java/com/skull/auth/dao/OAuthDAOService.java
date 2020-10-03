package com.skull.auth.dao;

import com.skull.auth.model.UserEntity;

public interface OAuthDAOService {

	public UserEntity getUserDetails(String emailId);
}

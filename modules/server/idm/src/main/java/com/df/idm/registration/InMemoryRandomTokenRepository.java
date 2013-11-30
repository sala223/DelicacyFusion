package com.df.idm.registration;

import com.df.idm.entity.User;

public class InMemoryRandomTokenRepository implements RandomTokenRepository {

	@Override
	public RandomToken generateAndSaveToken(User newUser) {
		RandomToken token = new RandomToken();
		token.setToken("dfdfd");
		return token;
	}

	@Override
	public boolean isValidToken(String token) {
		return true;
	}

}

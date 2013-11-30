package com.df.idm.registration;

import com.df.idm.entity.User;

public interface RandomTokenRepository {

	RandomToken generateAndSaveToken(User newUser);

	boolean isValidToken(String token);
}

package com.df.idm.registration;

import com.df.idm.entity.User;

public interface EmailRegistrationTokenRepository {

	void setValidDuration(int minutes);

	RandomToken generateAndSaveToken(User newUser);

	String isValidToken(String token);
}

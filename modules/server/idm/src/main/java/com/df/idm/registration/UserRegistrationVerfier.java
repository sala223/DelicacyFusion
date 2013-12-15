package com.df.idm.registration;

import java.util.Map;

import com.df.idm.entity.User;

public interface UserRegistrationVerfier {

	public static String DEFAULT_TOKEN_PARAMETER = "token";

	public static String DEFAULT_VERIFY_URL_PREFIX = "/registration/verify";

	void sentRegistrationMesage(User newUser, Map<String, Object> attributes);

	String verifyRegistration(String token);
}

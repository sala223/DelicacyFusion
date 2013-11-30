package com.df.idm.registration;

import com.df.idm.entity.User;

public interface UserRegistrationVerfier {

	RandomToken sentRegistrationMesage(User newUser);

	boolean verifyRegistration(String token);
}

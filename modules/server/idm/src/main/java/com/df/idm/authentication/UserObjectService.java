package com.df.idm.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserObjectService extends UserDetailsService {

	UserObject getUserByEmail(String email);

	UserObject getUserByCellPhone(String cellphone);
}

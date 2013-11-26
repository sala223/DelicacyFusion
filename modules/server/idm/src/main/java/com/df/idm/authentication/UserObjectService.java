package com.df.idm.authentication;

public interface UserObjectService {

	UserObject getUserByEmail(String email);

	UserObject getUserByCellPhone(String cellphone);
}

package com.df.idm.authentication;

public interface UserObjectService {

	UserObject getUserByEmailOrTelephone(String emailOrTelehone);

	UserObject getUserByWeiboAccount(String weiboAccount);
}

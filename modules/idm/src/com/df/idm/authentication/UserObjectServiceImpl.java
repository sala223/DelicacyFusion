package com.df.idm.authentication;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.idm.entity.User;
import com.df.idm.exception.UserNotFoundException;
import com.df.idm.service.contract.IUserManagementService;

public class UserObjectServiceImpl implements UserObjectService {

	@Autowired
	private IUserManagementService userManagementService;

	public UserObjectServiceImpl(IUserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	public void setUserManagementService(IUserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	@Override
	public UserObject getUserByEmailOrTelephone(String emailOrTelehone) {
		User user = userManagementService.getUserByEmail(emailOrTelehone);
		if (user == null) {
			user = userManagementService.getUserByTelephone(emailOrTelehone);
		}
		if (user == null) {
			throw new UserNotFoundException(String.format("User %s is not found", emailOrTelehone));
		}
		return new UserObject(user);
	}

	@Override
	public UserObject getUserByWeiboAccount(String weiboAccount) {
		User user = userManagementService.getUserByWeiboAccount(weiboAccount);
		if (user == null) {
			throw new UserNotFoundException(String.format("User weib account %s is not found", weiboAccount));
		}
		return new UserObject(user);

	}
}

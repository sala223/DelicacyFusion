package com.df.idm.authentication;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.idm.entity.User;
import com.df.idm.exception.UserNotFoundException;
import com.df.idm.service.contract.UserManagementService;

public class UserObjectServiceImpl implements UserObjectService {

	@Autowired
	private UserManagementService userManagementService;

	public UserObjectServiceImpl(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	public void setUserManagementService(UserManagementService userManagementService) {
		this.userManagementService = userManagementService;
	}

	@Override
	public UserObject getUserByEmail(String emailOrTelehone) {
		User user = userManagementService.getUserByEmail(emailOrTelehone);
		if (user == null) {
			throw new UserNotFoundException(String.format("User with mail %s is not found", emailOrTelehone));
		}
		return new UserObject(user);
	}

	@Override
	public UserObject getUserByCellPhone(String weiboAccount) {
		User user = userManagementService.getUserByWeiboAccount(weiboAccount);
		if (user == null) {
			throw new UserNotFoundException(String.format("User with cell phone %s is not found", weiboAccount));
		}
		return new UserObject(user);

	}
}

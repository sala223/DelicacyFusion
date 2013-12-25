package com.df.idm.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
	public UserObject getUserByCellPhone(String cellPhone) {
		User user = userManagementService.getUserByCellPhone(cellPhone);
		if (user == null) {
			throw new UserNotFoundException(String.format("User with cell phone %s is not found", cellPhone));
		}
		return new UserObject(user);

	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return this.getUserByEmail(userName);
	}
}

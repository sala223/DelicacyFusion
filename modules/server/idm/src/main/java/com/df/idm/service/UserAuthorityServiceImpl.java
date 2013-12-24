package com.df.idm.service;

import com.df.idm.dao.UserRoleDao;
import com.df.idm.entity.RoleId;
import com.df.idm.entity.User;
import com.df.idm.service.contract.UserAuthorityService;

public class UserAuthorityServiceImpl implements UserAuthorityService {

	private UserRoleDao userRoleDao;

	public UserAuthorityServiceImpl(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	@Override
	public void assign(User user, RoleId role) {
		userRoleDao.setUserRole(user, role);
	}

	@Override
	public void revoke(User user, RoleId role) {
		userRoleDao.removeUserRole(user, role);
	}

}

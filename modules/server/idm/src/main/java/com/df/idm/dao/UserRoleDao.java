package com.df.idm.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.idm.entity.Constants;
import com.df.idm.entity.Role;
import com.df.idm.entity.RoleId;
import com.df.idm.entity.User;
import com.df.idm.exception.RoleException;

public class UserRoleDao extends EclipseLinkDataAccessFoundation implements Constants {

	@Autowired
	private UserDao userDao;

	public UserRoleDao() {
	}

	public UserRoleDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserRole(User user, RoleId id) {
		Role role = this.findRole(id);
		if (role == null) {
			throw RoleException.roleNotExist(id);
		} else {
			user.addRole(id);
			userDao.update(user);
		}
	}

	public void removeUserRole(User user, RoleId id) {
		user.removeRole(id);
		userDao.update(user);
	}

	public Role findRole(RoleId roleId) {
		return this.getEntityManager().find(Role.class, roleId);
	}
}

package com.df.idm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.df.idm.dal.UserDAL;
import com.df.idm.entity.User;
import com.df.idm.service.contract.IUserManagementService;

@Transactional
public class UserManagementService implements IUserManagementService {

	@Autowired
	protected UserDAL userDAL;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	public UserManagementService(UserDAL userDAL, PasswordEncoder passwordEncoder) {
		this.userDAL = userDAL;
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserDAL(UserDAL userDAL) {
		this.userDAL = userDAL;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDAL.createUser(user);
	}

	public void updateUser(User user) {
		userDAL.update(user);
	}

	public void updatePassword(String emailOrTelephone, String newPassword) {
		User user = userDAL.getUserByEmail(emailOrTelephone);
		if (user == null) {
			user = userDAL.getUserByTelephone(emailOrTelephone);
		}

		if (user != null) {
			String encodePassword = passwordEncoder.encode(newPassword);
			userDAL.updateUserPassword(emailOrTelephone, encodePassword);
		}
	}

	@Override
	public User getUserByWeiboAccount(String weiboAccount) {
		return userDAL.getUserByWeiboAccount(weiboAccount);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDAL.getUserByEmail(email);
	}

	@Override
	public User getUserByTelephone(String telephone) {
		return userDAL.getUserByTelephone(telephone);
	}

	public void deleteUser(long userId) {
		userDAL.remove(User.class, userId);
	}
}

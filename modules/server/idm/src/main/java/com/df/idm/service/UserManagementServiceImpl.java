package com.df.idm.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.df.idm.dal.UserDao;
import com.df.idm.entity.User;
import com.df.idm.exception.UserException;
import com.df.idm.service.contract.UserManagementService;

@Transactional
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	protected UserDao userDao;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	public UserManagementServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.createUser(user);
	}

	public void updateUser(User user) {
		User found = userDao.getUserByEmail(user.getEmail());
		if (found != null) {
			String password = found.getPassword();
			String weiboAccount = found.getWeiboAccount();
			String cellPhone = found.getCellPhone();
			user.setId(found.getId());
			user.setCreatedTime(found.getCreatedTime());
			user.setChangedTime(new Date());
			user.setPassword(password);
			user.setWeiboAccount(weiboAccount);
			user.setCellPhone(cellPhone);
			userDao.update(user);
		} else {
			throw UserException.userEmailNotFound(user.getEmail());
		}

		userDao.update(user);
	}

	public void updatePassword(String emailOrTelephone, String newPassword) {
		User user = userDao.getUserByEmail(emailOrTelephone);
		if (user == null) {
			user = userDao.getUserByTelephone(emailOrTelephone);
		}

		if (user != null) {
			String encodePassword = passwordEncoder.encode(newPassword);
			userDao.updateUserPassword(emailOrTelephone, encodePassword);
		}
	}

	@Override
	public User getUserByWeiboAccount(String weiboAccount) {
		return userDao.getUserByWeiboAccount(weiboAccount);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	public User getUserByTelephone(String telephone) {
		return userDao.getUserByTelephone(telephone);
	}

	public void deleteUser(long userId) {
		userDao.remove(User.class, userId);
	}
}

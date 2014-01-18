package com.df.idm.service;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.df.core.validation.exception.ValidationException;
import com.df.idm.dao.UserDao;
import com.df.idm.entity.ExternalUserReference;
import com.df.idm.entity.ExternalUserReference.Provider;
import com.df.idm.entity.User;
import com.df.idm.exception.UserException;
import com.df.idm.registration.UserRegistrationVerfier;
import com.df.idm.service.contract.UserManagementService;
import com.df.idm.validation.group.InternalUserCreation;

@Transactional
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRegistrationVerfier registrationVerifier;

	@Autowired
	private Validator validator;

	public UserManagementServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRegistrationVerifier(UserRegistrationVerfier registrationVerifier) {
		this.registrationVerifier = registrationVerifier;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public User createUser(User user) {
		Set<ConstraintViolation<User>> violations = validator.validate(user, Default.class, InternalUserCreation.class);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		User found = userDao.getUserByEmail(user.getEmail());
		if (found != null) {
			throw UserException.userEmailAlreadyExist(user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEmailVerified(false);
		user.setCellphoneVerified(false);
		userDao.createUser(user);
		registrationVerifier.sentRegistrationMesage(user, null);
		return user;
	}

	public User updateUser(User user) {
		Set<ConstraintViolation<User>> violations = validator.validate(user,Default.class, InternalUserCreation.class);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		User found = userDao.getUserByEmail(user.getEmail());
		if (found != null) {
			found.setAge(user.getAge());
			found.setFirstName(user.getFirstName());
			found.setGender(user.getGender());
			found.setLastName(user.getLastName());
			found.setNickName(user.getNickName());
			found.setChangedTime(new Date());
			userDao.update(found);
		} else {
			throw UserException.userEmailNotFound(user.getEmail());
		}
		user.setChangedTime(found.getChangedTime());
		return user;
	}

	public void updatePassword(String emailOrTelephone, String oldPassword, String newPassword) {
		User user = userDao.getUserByEmail(emailOrTelephone);
		if (user == null) {
			user = userDao.getUserByTelephone(emailOrTelephone);
		}

		if (user != null) {
			String encodedOldPassword = passwordEncoder.encode(oldPassword);
			if (!user.getPassword().equals(encodedOldPassword)) {
				throw UserException.userPasswordNotMatch(emailOrTelephone);
			}
			String encodedPassword = passwordEncoder.encode(newPassword);
			userDao.updateUserPassword(emailOrTelephone, encodedPassword);
		}
	}

	@Override
	public User getUserByExternalId(String externalId, Provider provider) {
		return userDao.getUserByExternalId(externalId, provider);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	public User getUserByCellPhone(String cellPhone) {
		return userDao.getUserByTelephone(cellPhone);
	}

	public void deleteUser(long userId) {
		userDao.remove(User.class, userId);
	}

	@Override
	public boolean verifyUserEmail(String token) {
		String email = registrationVerifier.verifyRegistration(token);
		if (email == null) {
			return false;
		}
		User found = userDao.getUserByEmail(email);
		if (found == null) {
			return false;
		}

		if (found.isEmailVerified()) {
			return true;
		}
		found.setEmailVerified(true);
		userDao.update(found);
		return true;
	}

	@Override
	public User getUserById(long userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public User addUserToTenant(long userId, String tenantCode) {
		User found = userDao.getUserById(userId);
		if (found != null) {
			found.addToTenantUser(tenantCode);
			userDao.update(found);
		} else {
			throw UserException.userIdNotFound(userId);
		}
		return found;
	}

	@Override
	public User removeUserFromTenant(long userId, String tenantCode) {
		User found = userDao.getUserById(userId);
		if (found != null) {
			found.removeFromTenantUser(tenantCode);
			userDao.update(found);
		} else {
			throw UserException.userIdNotFound(userId);
		}
		return found;

	}

	@Override
	public User createOrUpdateExternalUser(ExternalUserReference externalUser) {
		User found = userDao.getUserByExternalId(externalUser.getExternalId(), externalUser.getProvider());
		if (found == null) {
			found = new User();
			found.addOrUpdateExternalReference(externalUser);
			userDao.insert(found);
		}
		return found;
	}
}

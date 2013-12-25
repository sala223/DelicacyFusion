package com.df.idm.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.df.core.validation.exception.ValidationException;
import com.df.idm.entity.User;
import com.df.idm.service.contract.UserManagementService;

public class UserManagementServiceTest extends IdmBaseTest {

	@Autowired
	private UserManagementService ums;

	@Test(expected = ValidationException.class)
	public void testNewUserWithoutPassword() {
		User user = new User();
		user.setEmail("sala223@msn.com");
		user.setCellPhone("13121992122");
		ums.createUser(user);
	}

	@Test(expected = ValidationException.class)
	public void testNewUserWithoutEmail() {
		User user = new User();
		user.setPassword("343433");
		user.setCellPhone("13121992122");
		user.setPassword("123456");
		ums.createUser(user);
	}

	@Test(expected = ValidationException.class)
	public void testNewUserErrorEmailFormat() {
		User user = new User();
		user.setPassword("343433");
		user.setEmail("dfdf");
		user.setCellPhone("13121992122");
		ums.createUser(user);
	}
}

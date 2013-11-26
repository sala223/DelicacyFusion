package com.df.idm.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.idm.dal.UserDAL;
import com.df.idm.entity.User;

@Transactional
public class UserDALTest extends IdmBaseTest {

	@Autowired
	private UserDAL userDAL;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("sala223@msn.com");
		user.setTelephone("13121992122");
		userDAL.insert(user);
	}

	@Test
	public void testFindUserByMail() {
		User user = new User();
		String email = "sala223@msn.com";
		user.setEmail(email);
		user.setTelephone("13121992122");
		user.setWeiboAccount("sala223");
		userDAL.insert(user);
		userDAL.getEntityManager().flush();
		TestCase.assertNotNull(userDAL.getUserByEmail(email));
	}

	@Test
	public void testFindUserByWeiboAccount() {
		User user = new User();
		String weiboAccount = "sala223";
		user.setEmail("sala223@msn.com");
		user.setTelephone("13121992122");
		user.setWeiboAccount(weiboAccount);
		userDAL.insert(user);
		userDAL.getEntityManager().flush();
		TestCase.assertNotNull(userDAL.getUserByWeiboAccount(weiboAccount));
	}

	@Test
	public void testFindUserByTelephone() {
		User user = new User();
		String telephone = "13121992122";
		user.setEmail("sala223@msn.com");
		user.setTelephone(telephone);
		userDAL.insert(user);
		userDAL.getEntityManager().flush();
		TestCase.assertNotNull(userDAL.getUserByTelephone(telephone));
	}
}

package com.df.idm.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.idm.dao.UserDao;
import com.df.idm.entity.User;

@Transactional
public class UserDaoTest extends IdmBaseTest {

	@Autowired
	private UserDao userDao;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("sala223@msn.com");
		user.setPassword("123456"); 
		user.setCellPhone("13121992122");
		userDao.insert(user);
	}

	@Test
	public void testFindUserByMail() {
		User user = new User();
		String email = "sala223@msn.com";
		user.setEmail(email);
		user.setCellPhone("13121992122");
		user.setWeiboAccount("sala223");
		user.setPassword("123456"); 
		userDao.insert(user);
		userDao.getEntityManager().flush();
		TestCase.assertNotNull(userDao.getUserByEmail(email));
	}

	@Test
	public void testFindUserByWeiboAccount() {
		User user = new User();
		String weiboAccount = "sala223";
		user.setEmail("sala223@msn.com");
		user.setPassword("123456"); 
		user.setCellPhone("13121992122");
		user.setWeiboAccount(weiboAccount);
		userDao.insert(user);
		userDao.getEntityManager().flush();
		TestCase.assertNotNull(userDao.getUserByWeiboAccount(weiboAccount));
	}

	@Test
	public void testFindUserByTelephone() {
		User user = new User();
		String telephone = "13121992122";
		user.setEmail("sala223@msn.com");
		user.setPassword("123456"); 
		user.setCellPhone(telephone);
		userDao.insert(user);
		userDao.getEntityManager().flush();
		TestCase.assertNotNull(userDao.getUserByTelephone(telephone));
	}
}

package com.df.idm.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.idm.entity.User;
import com.df.idm.registration.UserRegistrationVerfier;

@Transactional
public class MailTest extends IdmBaseTest {

	@Autowired
	private UserRegistrationVerfier verifier;

	@Test
	public void testSendUserVerificationMail() {
		User user = new User();
		user.setEmail("sala223@msn.com");
		verifier.sentRegistrationMesage(user);

	}
}

package com.df.idm.test;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.df.idm.authentication.UserObject;
import com.df.idm.authentication.UserPropertyAuthenticationToken;
import com.df.idm.entity.User;
import com.df.idm.service.contract.IUserManagementService;

@Transactional
public class AuthenticationServiceTest extends IdmBaseTest {

	@Inject
	private AuthenticationProvider authProvider;

	@Inject
	private IUserManagementService userManagementService;

	@Test
	public void testAuthenticationProviderWithCorrectAccount() {
		String email = "sala223@msn.com";
		String telephone = "1352992345";
		String password = "Oba.2247d";
		User user = new User();
		user.setEmail(email);
		user.setTelephone(telephone);
		user.setPassword(password);
		userManagementService.createUser(user);
		UserPropertyAuthenticationToken authToken = new UserPropertyAuthenticationToken(email, password);
		Authentication authentication = authProvider.authenticate(authToken);
		TestCase.assertTrue(authentication instanceof UserPropertyAuthenticationToken);
		UserPropertyAuthenticationToken token = (UserPropertyAuthenticationToken) authentication;
		TestCase.assertEquals(token.getPrincipal().getClass(), UserObject.class);
		UserObject userObject = (UserObject) token.getPrincipal();
		TestCase.assertEquals(userObject.getTelephone(), telephone);
		userManagementService.deleteUser(user.getId());
	}
}

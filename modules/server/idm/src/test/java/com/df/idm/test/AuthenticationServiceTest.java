package com.df.idm.test;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.df.idm.authentication.UserObject;
import com.df.idm.authentication.UserObjectPropertyAuthenticationToken;
import com.df.idm.entity.User;
import com.df.idm.service.contract.UserManagementService;

@Transactional
public class AuthenticationServiceTest extends IdmBaseTest {

	@Inject
	private AuthenticationProvider authProvider;

	@Inject
	private UserManagementService userManagementService;

	@Test
	public void testAuthenticationProviderWithEmailAccount() {
		String email = "sala223@msn.com";
		String telephone = "1352992345";
		String password = "Oba.2247d";
		User user = new User();
		user.setEmail(email);
		user.setCellPhone(telephone);
		user.setPassword(password);
		userManagementService.createUser(user);
		UserObjectPropertyAuthenticationToken authToken = new UserObjectPropertyAuthenticationToken(email, password);
		Authentication authentication = authProvider.authenticate(authToken);
		TestCase.assertTrue(authentication instanceof UserObjectPropertyAuthenticationToken);
		UserObjectPropertyAuthenticationToken token = (UserObjectPropertyAuthenticationToken) authentication;
		TestCase.assertEquals(token.getPrincipal().getClass(), UserObject.class);
		UserObject userObject = (UserObject) token.getPrincipal();
		TestCase.assertEquals(userObject.getCellphone(), telephone);
		userManagementService.deleteUser(user.getId());
	}

	@Test
	public void testAuthenticationProviderWithTelephoneAccount() {
		String email = "sala223@msn.com";
		String telephone = "1352992345";
		String password = "Oba.2247d";
		User user = new User();
		user.setEmail(email);
		user.setCellPhone(telephone);
		user.setPassword(password);
		userManagementService.createUser(user);
		UserObjectPropertyAuthenticationToken authToken = new UserObjectPropertyAuthenticationToken(telephone, password);
		Authentication authentication = authProvider.authenticate(authToken);
		TestCase.assertTrue(authentication instanceof UserObjectPropertyAuthenticationToken);
		UserObjectPropertyAuthenticationToken token = (UserObjectPropertyAuthenticationToken) authentication;
		TestCase.assertEquals(token.getPrincipal().getClass(), UserObject.class);
		UserObject userObject = (UserObject) token.getPrincipal();
		TestCase.assertEquals(userObject.getCellphone(), telephone);
		userManagementService.deleteUser(user.getId());
	}

	@Test
	public void testAuthenticationProviderWithIncorrectAccount() {
		String email = "sala223@msn.com";
		String telephone = "1352992345";
		String password = "Oba.2247d";
		User user = new User();
		user.setEmail(email);
		user.setCellPhone(telephone);
		user.setPassword(password);
		userManagementService.createUser(user);
		UserObjectPropertyAuthenticationToken authToken = new UserObjectPropertyAuthenticationToken("errorAccount",
		        password);
		try {
			authProvider.authenticate(authToken);
			TestCase.fail();
		} catch (BadCredentialsException ex) {
		}
		userManagementService.deleteUser(user.getId());
	}
}

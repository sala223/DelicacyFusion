package com.df.idm.authentication;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.df.idm.exception.UserNotFoundException;

public class InternalAuthenticationProvider extends UserObjectAuthenticationProvider {

	private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

	private UserObjectService userObjectService;

	
	public InternalAuthenticationProvider(UserObjectService userObjectService) {
		this.userObjectService = userObjectService;
	}

	@Override
	protected UserObject retrieveUser(String userId, UserPropertyAuthenticationToken authentication)
			throws AuthenticationException {
		UserObject found;
		try {
			found = this.getUserObjectService().getUserByEmailOrTelephone(userId);
			if (found == null) {
				throw new UserNotFoundException("Unknow user email or telephone " + userId);
			}
		}
		catch (UserNotFoundException notFound) {
			throw notFound;
		}
		catch (Exception repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}
		return found;
	}

	protected void additionalAuthenticationChecks(UserObject userObject, UserPropertyAuthenticationToken authentication)
			throws AuthenticationException {
		String msg = messages.getMessage("authentication.badCredentials", "Bad Credential");
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(msg);
		}
		String presentedPassword = authentication.getCredentials().toString();
		if (!passwordEncoder.matches(presentedPassword,userObject.getPassword())) {
			logger.debug("Authentication failed: password does not match stored value");
			throw new BadCredentialsException(msg);
		}
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	protected UserObjectService getUserObjectService() {
		return userObjectService;
	}

	public void setUserObjectService(UserObjectService userObjectService) {
		this.userObjectService = userObjectService;
	}

}

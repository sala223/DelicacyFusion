package com.df.idm.authentication;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;

import com.df.idm.exception.UserNotFoundException;

public abstract class UserObjectAuthenticationProvider implements AuthenticationProvider {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private boolean forcePrincipalAsString = false;

	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

	private UserObjectChecker preAuthenticationChecks;

	private UserObjectChecker postAuthenticationChecks;

	protected MessageSourceAccessor messages = SecurityMessageSource.getAccessor();

	protected abstract void additionalAuthenticationChecks(UserObject user,
	        UserObjectPropertyAuthenticationToken authentication) throws AuthenticationException;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!(supports(authentication.getClass()))) {
			return null;
		}
		UserObjectPropertyAuthenticationToken mtat = (UserObjectPropertyAuthenticationToken) authentication;
		String userId = (authentication.getPrincipal() == null) ? "" : authentication.getName();
		UserObject user = null;
		try {
			if (mtat.isMailAccount()) {
				user = this.retrieveUserByEmail(userId, mtat);
			} else {
				user = this.retrieveUserByCellPhone(userId, mtat);
			}
			mtat.setDetails(user);
		} catch (UserNotFoundException notFound) {
			String msg = messages.getMessage("authentication.badCredentials", "Bad Credential");
			throw new BadCredentialsException(msg);
		}
		if (preAuthenticationChecks != null) {
			preAuthenticationChecks.check(user);
		}
		additionalAuthenticationChecks(user, mtat);
		if (postAuthenticationChecks != null) {
			postAuthenticationChecks.check(user);
		}
		Object principalToReturn = user;
		return createSuccessAuthentication(principalToReturn, authentication, user);
	}

	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
	        UserObject user) {
		Collection<? extends GrantedAuthority> mapAuthorities = authoritiesMapper.mapAuthorities(user.getAuthorities());
		UserObjectPropertyAuthenticationToken result = new UserObjectPropertyAuthenticationToken(principal,
		        mapAuthorities);
		result.setAuthenticated(true);
		result.setDetails(user);
		SecurityContextHolder.getContext().setAuthentication(result);
		return result;
	}

	public boolean isForcePrincipalAsString() {
		return forcePrincipalAsString;
	}

	protected abstract UserObject retrieveUserByEmail(String email, UserObjectPropertyAuthenticationToken authentication)
	        throws AuthenticationException;

	protected abstract UserObject retrieveUserByCellPhone(String cellPhone,
	        UserObjectPropertyAuthenticationToken authentication) throws AuthenticationException;

	public boolean supports(Class<?> authentication) {
		return (UserObjectPropertyAuthenticationToken.class.isAssignableFrom(authentication));
	}

	protected UserObjectChecker getPreAuthenticationChecks() {
		return preAuthenticationChecks;
	}

	public void setPreAuthenticationChecks(UserObjectChecker preAuthenticationChecks) {
		this.preAuthenticationChecks = preAuthenticationChecks;
	}

	protected UserObjectChecker getPostAuthenticationChecks() {
		return postAuthenticationChecks;
	}

	public void setPostAuthenticationChecks(UserObjectChecker postAuthenticationChecks) {
		this.postAuthenticationChecks = postAuthenticationChecks;
	}

	public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
		this.authoritiesMapper = authoritiesMapper;
	}

}

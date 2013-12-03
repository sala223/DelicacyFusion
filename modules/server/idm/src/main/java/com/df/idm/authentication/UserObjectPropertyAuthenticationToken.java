package com.df.idm.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.df.core.common.utils.StringUtils;

public class UserObjectPropertyAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private Object principal;

	private Object credentials;

	public UserObjectPropertyAuthenticationToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.setAuthenticated(false);
	}

	public UserObjectPropertyAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public boolean isMailAccount() {
		return StringUtils.isValidEmail(principal.toString());
	}

}

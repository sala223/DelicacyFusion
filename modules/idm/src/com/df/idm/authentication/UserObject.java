package com.df.idm.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.df.idm.entity.User;

public class UserObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	public UserObject(User user) {
		Assert.notNull(user);
		this.user = user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>();
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getEmail() {
		return user.getEmail();
	}

	public String getTelephone() {
		return user.getTelephone();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return !user.isLocked();
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}

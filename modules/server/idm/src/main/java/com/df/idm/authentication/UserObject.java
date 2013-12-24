package com.df.idm.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.df.idm.entity.Role;
import com.df.idm.entity.RoleId;
import com.df.idm.entity.User;

public class UserObject implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;

	public UserObject(User user) {
		Assert.notNull(user);
		this.user = user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<RoleId> roleIds = this.user.getRoles();
		List<Role> roles = new ArrayList<Role>();
		for (RoleId id : roleIds) {
			roles.add(new Role(id));
		}
		return roles;
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getEmail() {
		return user.getEmail();
	}

	public String getCellphone() {
		return user.getCellPhone();
	}

	public String getWeiboAccount() {
		return user.getWeiboAccount();
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

	@Override
	public String getUsername() {
		return this.getEmail();
	}

	public boolean isTenantOwner() {
		return user.isTenantOwner();
	}

	public String getTenantCode() {
		return user.getTenantCode();
	}

	public long getUserId() {
		return user.getId();
	}
}

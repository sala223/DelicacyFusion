package com.df.idm.authentication;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.df.idm.entity.RoleId;
import com.df.idm.entity.User;

public class UserObject implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String email;

	private String cellPhone;

	private String weiboAccount;

	private boolean isLocked;

	private String password;

	private List<RoleId> roles;

	private long userId;

	private boolean isTenantUser;

	private String tenant;

	public UserObject(User user) {
		Assert.notNull(user);
		this.email = user.getEmail();
		this.cellPhone = user.getCellPhone();
		this.weiboAccount = user.getWeiboAccount();
		this.isLocked = user.isLocked();
		this.password = user.getPassword();
		this.roles = user.getRoles();
		this.userId = user.getId();
		this.isTenantUser = user.isTenantUser();
		if (user.getTenants().size() > 0) {
			this.tenant = user.getTenants().get(0);
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public String getCellphone() {
		return this.cellPhone;
	}

	public String getWeiboAccount() {
		return this.weiboAccount;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return !this.isLocked;
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

	public boolean isTenantUser() {
		return this.isTenantUser;
	}

	public String getTenant() {
		return this.tenant;
	}

	public long getUserId() {
		return this.userId;
	}

	public UserObject eraseCredential() {
		this.password = null;
		return this;
	}
}

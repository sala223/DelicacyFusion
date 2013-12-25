package com.df.idm.authentication;

import java.io.Serializable;
import org.springframework.util.StringUtils;

public class UserContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserObject user;

	UserContext() {
	}

	public UserContext(UserObject user) {
		this.user = user;
	}

	public long getUserId() {
		return user.getUserId();
	}

	public String getEmail() {
		return this.user.getEmail();
	}

	public String getCellPhone() {
		return this.user.getCellphone();
	}

	public boolean isTenantUser() {
		return this.user.isTenantUser();
	}

	public String getTenant() {
		return this.user.getTenant();
	}

	public String[] getAuthorities() {
		String authorities = StringUtils.collectionToCommaDelimitedString(user.getAuthorities());
		return StringUtils.commaDelimitedListToStringArray(authorities);
	}

}

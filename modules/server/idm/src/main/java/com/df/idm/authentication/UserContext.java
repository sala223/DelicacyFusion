package com.df.idm.authentication;

import java.io.Serializable;

public class UserContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserObject user;

	UserContext() {
	}

	public UserContext(UserObject user) {
		this.user = user;
	}

	public String getEmail() {
		return this.user.getEmail();
	}

	public String getCellPhone() {
		return this.user.getCellphone();
	}

	public boolean isTenantOwner() {
		return this.user.isTenantOwner();
	}

	public String getTenantCode() {
		return this.user.getTenantCode();
	}

}

package com.df.idm.authorization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import com.df.idm.authentication.UserObject;
import com.df.idm.entity.RoleId;

public class TenantLevelMethodSecurityExpressionRoot extends SecurityExpressionRoot implements
        TenantLevelSecurityExpressionOperations {

	private List<RoleId> roles;

	private Object target;

	private Object filterObject;

	private Object returnObject;

	public TenantLevelMethodSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	@Override
	public boolean hasTenantRole(String tenantCode, String role) {
		if (StringUtils.isEmpty(tenantCode)) {
			throw new IllegalArgumentException("tenant code must not be empty");
		}
		List<RoleId> authorities = this.getAuthorities();
		for (RoleId authority : authorities) {
			if (tenantCode.equals(authority.getDomain())) {
				if (authority.getName().equals(role)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean hasTenantAnyRole(String tenantCode, String... roles) {
		if (roles == null) {
			throw new IllegalArgumentException("roles must not be null");
		}
		for (String role : roles) {
			boolean hasRole = this.hasTenantRole(tenantCode, role);
			if (hasRole) {
				return true;
			}
		}
		return false;
	}

	protected List<RoleId> getAuthorities() {
		if (roles != null) {
			return roles;
		}
		roles = new ArrayList<RoleId>();
		Object details = this.getAuthentication().getDetails();
		if (details instanceof UserObject) {
			UserObject userObject = (UserObject) details;
			Collection<? extends GrantedAuthority> authorities = userObject.getAuthorities();
			for (GrantedAuthority authority : authorities) {
				if (authority instanceof RoleId) {
					roles.add((RoleId) authority);
				}
			}
		}
		return roles;
	}

	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

	public Object getFilterObject() {
		return this.filterObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public Object getReturnObject() {
		return this.returnObject;
	}

	void setThis(Object target) {
		this.target = target;
	}

	public Object getThis() {
		return this.target;
	}
}

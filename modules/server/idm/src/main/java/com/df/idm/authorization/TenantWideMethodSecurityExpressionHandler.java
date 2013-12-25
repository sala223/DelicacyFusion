package com.df.idm.authorization;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class TenantWideMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
	        MethodInvocation invocation) {
		TenantWideMethodSecurityExpressionRoot root = new TenantWideMethodSecurityExpressionRoot(authentication);
		root.setThis(invocation.getThis());
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(new AuthenticationTrustResolverImpl());
		root.setRoleHierarchy(getRoleHierarchy());
		return root;
	}
}

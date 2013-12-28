package com.df.idm.authorization;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class TenantLevelMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
	        MethodInvocation invocation) {
		TenantLevelMethodSecurityExpressionRoot root = new TenantLevelMethodSecurityExpressionRoot(authentication);
		root.setThis(invocation.getThis());
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(new AuthenticationTrustResolverImpl());
		root.setRoleHierarchy(getRoleHierarchy());
		return root;
	}
}

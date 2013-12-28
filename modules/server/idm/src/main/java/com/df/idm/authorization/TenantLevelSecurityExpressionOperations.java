package com.df.idm.authorization;

import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;

public interface TenantLevelSecurityExpressionOperations extends SecurityExpressionOperations,
        MethodSecurityExpressionOperations {

	boolean hasTenantRole(String tenantCode, String role);

	boolean hasTenantAnyRole(String tenantCode, String... roles);

}

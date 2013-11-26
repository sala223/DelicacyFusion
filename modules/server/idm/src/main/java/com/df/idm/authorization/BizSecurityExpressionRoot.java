package com.df.idm.authorization;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

public class BizSecurityExpressionRoot extends SecurityExpressionRoot {

	public BizSecurityExpressionRoot(Authentication a) {
		super(a);
	}

}

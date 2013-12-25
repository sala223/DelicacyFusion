package com.df.idm.authentication;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.df.idm.entity.User;

public class UserContextHelper {

	public static UserContext getCurrentUser() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		Object obj = attributes.getAttribute(Constants.USER_CONTEXT_SESSION_ATTR, RequestAttributes.SCOPE_SESSION);
		return (UserContext) obj;
	}

	public static UserContext updateUserContext(User user) {
		UserObject userObject = new UserObject(user);
		UserContext userContext = new UserContext(userObject.eraseCredential());
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		attributes.setAttribute(Constants.USER_CONTEXT_SESSION_ATTR, userContext, RequestAttributes.SCOPE_SESSION);
		return userContext;
	}
}

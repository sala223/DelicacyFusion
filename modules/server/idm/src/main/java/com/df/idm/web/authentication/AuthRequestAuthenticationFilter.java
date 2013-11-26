package com.df.idm.web.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.df.idm.authentication.UserObjectPropertyAuthenticationToken;

public class AuthRequestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String DEFAULT_FILTER_PROCESS_URL = "/login";

	public static final String SECURITY_AUTH_USER_EMAIL_KEY = "df_user_id";

	public static final String SECURITY_AUTH_USER_PASSWORD_KEY = "df_user_password";

	protected AuthRequestAuthenticationFilter() {
		this(DEFAULT_FILTER_PROCESS_URL);

	}

	protected AuthRequestAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String userId = obtainUserId(request);
		String password = obtainUserPassword(request);
		if (userId == null) {
			userId = "";
		}
		if (password == null) {
			password = "";
		}
		userId = userId.trim();
		UserObjectPropertyAuthenticationToken authRequest = new UserObjectPropertyAuthenticationToken(userId, password);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainUserId(HttpServletRequest request) {
		return request.getParameter(SECURITY_AUTH_USER_EMAIL_KEY);
	}

	protected String obtainUserPassword(HttpServletRequest request) {
		return request.getParameter(SECURITY_AUTH_USER_PASSWORD_KEY);
	}

}

package com.df.idm.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class MailOrCellPhoneAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String DEFAULT_FILTER_PROCESS_URL = "/login";

	public static final String SECURITY_AUTH_ACCOUNT_KEY = "df_account";

	public static final String SECURITY_AUTH_PASSWORD_KEY = "df_password";

	private boolean postOnly = true;

	public MailOrCellPhoneAuthenticationFilter() {
		this(DEFAULT_FILTER_PROCESS_URL);

	}

	public MailOrCellPhoneAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		this.messages = SecurityMessageSource.getAccessor();
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
	        throws AuthenticationException, IOException, ServletException {
		if ((this.postOnly) && (!(request.getMethod().equals("POST")))) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String account = obtainAccount(request);
		String password = obtainUserPassword(request);
		if (account == null) {
			account = "";
		}
		if (password == null) {
			password = "";
		}
		account = account.trim();
		UserObjectPropertyAuthenticationToken authRequest = new UserObjectPropertyAuthenticationToken(account, password);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected String obtainAccount(HttpServletRequest request) {
		return request.getParameter(SECURITY_AUTH_ACCOUNT_KEY);
	}

	protected String obtainUserPassword(HttpServletRequest request) {
		return request.getParameter(SECURITY_AUTH_PASSWORD_KEY);
	}
}

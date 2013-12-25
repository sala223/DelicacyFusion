package com.df.idm.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class DirectAuthenticationFilter extends GenericFilterBean {

	private ObjectMapper objectMapper;

	private AuthenticationManager authenticationManager;

	private RequestMatcher requestMatcher;

	private static String DEFAULT_PROCESS_URL = "/login/direct";

	public DirectAuthenticationFilter() {
		this(DEFAULT_PROCESS_URL);
	}

	public DirectAuthenticationFilter(String processUrl) {
		requestMatcher = new AuthenticationRequestMatcher(processUrl);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
	        ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if (requestMatcher.matches(request)) {
			AuthenticationResponse response = new AuthenticationResponse();
			Authentication authentication = this.getAuthenticationToken(request);
			res.setContentType("application/json;charset=UTF-8");
			try {
				authentication = authenticationManager.authenticate(authentication);
				if (authentication == null) {
					response.setAuthenticated(false);
					response.setErrorMessage("Illegal request parameters");
				}
				response.setAuthenticated(true);
				Object details = authentication.getDetails();
				if (details != null && details instanceof UserObject) {
					UserContext userContext = new UserContext((UserObject) details);
					response.setUserContext(userContext);
					RequestAttributes attrs = RequestContextHolder.currentRequestAttributes();
					int scope = RequestAttributes.SCOPE_SESSION;
					attrs.setAttribute(Constants.USER_CONTEXT_SESSION_ATTR, userContext, scope);
				}
			} catch (AuthenticationException ex) {
				response.setAuthenticated(false);
				response.setErrorMessage(ex.getMessage());
			}
			objectMapper.writeValue(res.getOutputStream(), response);
			res.flushBuffer();
		} else {
			chain.doFilter(req, res);
		}
	}

	protected Authentication getAuthenticationToken(HttpServletRequest request) {
		try {
			ServletInputStream in = request.getInputStream();
			AuthenticationRequest req = objectMapper.readValue(in, AuthenticationRequest.class);
			return new UserObjectPropertyAuthenticationToken(req.getAccount(), req.getPassword());
		} catch (Throwable ex) {
			return null;
		}
	}

	private static final class AuthenticationRequestMatcher implements RequestMatcher {
		private final String filterProcessesUrl;

		private AuthenticationRequestMatcher(String filterProcessesUrl) {
			Assert.hasLength(filterProcessesUrl, "filterProcessesUrl must be specified");
			this.filterProcessesUrl = filterProcessesUrl;
		}

		public boolean matches(HttpServletRequest request) {
			String contentType = request.getContentType();
			String method = request.getMethod().toUpperCase();
			if (method.equals("POST") && contentType.toLowerCase().startsWith("application/json")) {
				String uri = request.getRequestURI();
				int pathParamIndex = uri.indexOf(59);

				if (pathParamIndex > 0) {
					uri = uri.substring(0, pathParamIndex);
				}

				if ("".equals(request.getContextPath())) {
					return uri.endsWith(this.filterProcessesUrl);
				}

				return uri.endsWith(request.getContextPath() + this.filterProcessesUrl);
			}
			return false;
		}
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setProcessUrl(String processUrl) {
		this.requestMatcher = new AuthenticationRequestMatcher(processUrl);
	}
}

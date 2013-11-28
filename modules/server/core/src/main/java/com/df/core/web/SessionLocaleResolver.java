package com.df.core.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.WebUtils;

public class SessionLocaleResolver implements LocaleResolver {

	public static final String LOCALE_SESSION_ATTRIBUTE_NAME = SessionLocaleResolver.class.getName() + ".LOCALE";

	private Locale defaultLocale;

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = (Locale) WebUtils.getSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME);
		if (locale == null) {
			locale = determineDefaultLocale(request);
		}
		return locale;
	}

	protected Locale determineDefaultLocale(HttpServletRequest request) {
		Locale defaultLocale = this.defaultLocale;
		if (defaultLocale == null) {
			defaultLocale = request.getLocale();
		}
		return defaultLocale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		WebUtils.setSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME, locale);
	}

	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

}

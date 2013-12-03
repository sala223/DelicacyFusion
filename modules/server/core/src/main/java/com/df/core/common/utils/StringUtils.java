package com.df.core.common.utils;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

public abstract class StringUtils extends org.springframework.util.StringUtils {

	private static final String EMAIL_REGULAR_EXPRESSION = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

	public static boolean isValidEmail(String email) {
		return Pattern.matches(EMAIL_REGULAR_EXPRESSION, email);
	}

	public static String normalizeEmail(String email) {
		Assert.notNull(email); 
		return email.trim().toLowerCase();
	}

}

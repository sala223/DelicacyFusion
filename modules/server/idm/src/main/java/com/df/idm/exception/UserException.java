package com.df.idm.exception;

import com.df.core.common.exception.BusinessException;

public class UserException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "User";

	public static final int USER_CODE_NOT_FOUND = 100000;

	public static final int USER_EMAIL_NOT_FOUND = 100001;

	public static final int USER_TELEPHONE_NOT_FOUND = 100002;

	public static final int USER_WEIBO_ACCOUNT_NOT_FOUND = 100003;

	public static final int USER_CODE_ALREADY_EXIST = 100004;

	public static final int USER_EMAIL_ALREADY_EXIST = 100005;

	public static final int USER_TELEPHONE_ALREADY_EXIST = 100006;

	public static final int USER_WEIBO_ACCOUNT_ALREADY_EXIST = 100007;

	public UserException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public UserException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public UserException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static UserException userCodeNotFound(String userCode) {
		String msg = "User code =%s is not found";
		return new UserException(USER_CODE_NOT_FOUND, msg, userCode);
	}

	public static UserException userEmailNotFound(String email) {
		String msg = "User email %s is not found.";
		return new UserException(USER_EMAIL_NOT_FOUND, msg, email);
	}

	public static UserException userTelephoneNotFound(String telephone) {
		String msg = "User telephone %s is not found";
		return new UserException(USER_TELEPHONE_NOT_FOUND, msg, telephone);
	}

	public static UserException userWeiboAccountNotFound(String weiboAccount) {
		String msg = "User weibo account %s is not found";
		return new UserException(USER_WEIBO_ACCOUNT_NOT_FOUND, msg, weiboAccount);
	}

	public static UserException userCodeAlreadyExist(String userCode) {
		String msg = "User code %s is already exist";
		return new UserException(USER_CODE_ALREADY_EXIST, msg, userCode);
	}

	public static UserException userEmailAlreadyExist(String email) {
		String msg = "User email %s is already exist";
		return new UserException(USER_EMAIL_ALREADY_EXIST, msg, email);
	}

	public static UserException userTelephoneAlreadyExist(String telephone) {
		String msg = "User telephone %s is already exist";
		return new UserException(USER_TELEPHONE_ALREADY_EXIST, msg, telephone);
	}

	public static UserException userWeiboAcountAlreadyExist(String weiboAccount) {
		String msg = "User weibo account %s is already exist";
		return new UserException(USER_WEIBO_ACCOUNT_ALREADY_EXIST, msg, weiboAccount);
	}

}

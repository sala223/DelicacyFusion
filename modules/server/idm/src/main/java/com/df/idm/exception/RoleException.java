package com.df.idm.exception;

import com.df.core.common.exception.BusinessException;
import com.df.idm.entity.RoleId;

public class RoleException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "Role";

	public static final int ROLE_NOT_EXIST = 100001;

	public RoleException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public RoleException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public RoleException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static RoleException roleNotExist(RoleId id) {
		String msg = "Role %s does not exist";
		return new RoleException(ROLE_NOT_EXIST, msg, id);
	}

}

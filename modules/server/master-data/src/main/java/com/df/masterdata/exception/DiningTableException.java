package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class DiningTableException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "DiningTable";

	public static final int TABLE_WITH_CODE_EXIST = 100000;

	public static final int TABLE_WITH_BAR_CODE_EXIST = 100001;

	public DiningTableException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public DiningTableException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public DiningTableException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static DiningTableException tableWithCodeAlreadyExist(String code) {
		return new DiningTableException(TABLE_WITH_CODE_EXIST, "Table code=%s is already exist.", code);
	}

	public static DiningTableException tableWithBarCodeAlreadyExist(String barCode) {
		return new DiningTableException(TABLE_WITH_BAR_CODE_EXIST, "Table barCode=%s is already exist.", barCode);
	}
}

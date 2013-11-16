package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class StoreException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "Store";

	public static final int STORE_WITH_NAME_ALREADY_EXIST = 100000;

	public static final int STORE_WITH_CODE_ALREADY_EXIST = 100001;

	public static final int STORE_WITH_CODE_NOT_EXIST = 100002;

	public StoreException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public StoreException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public StoreException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static StoreException storeWithNameAlreadyExist(String storeName) {
		return new StoreException(STORE_WITH_NAME_ALREADY_EXIST, "Store Name=%s is already exist.", storeName);
	}

	public static StoreException storeWithCodeAlreadyExist(String code) {
		return new StoreException(STORE_WITH_CODE_ALREADY_EXIST, "Store Code=%s is already exist.", code);
	}

	public static StoreException storeWithCodeNotExist(String storeCode) {
		return new StoreException(STORE_WITH_CODE_NOT_EXIST, "Store Code=%s does not exist.", storeCode);
	}
}

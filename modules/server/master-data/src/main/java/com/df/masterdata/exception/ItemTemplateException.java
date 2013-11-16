package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class ItemTemplateException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "ItemTemplate";

	public static final int ITEM_TPL_WITH_CODE_EXIST = 100000;

	public static final int ITEM_TPL_WITH_NAME_EXIST = 100001;

	public ItemTemplateException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public ItemTemplateException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public ItemTemplateException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static ItemTemplateException itemTemplateWithCodeExist(String code) {
		return new ItemTemplateException(ITEM_TPL_WITH_CODE_EXIST, "ItemTemplate Code=%s already exist.", code);
	}

	public static ItemTemplateException itemTemplateWithNameExist(String name) {
		return new ItemTemplateException(ITEM_TPL_WITH_NAME_EXIST, "ItemTemplate Name=%s already exist.", name);
	}
}

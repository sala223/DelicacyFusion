package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class ItemException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "Item";

	public static final int ITEM_WITH_CODE_NOT_EXIST = 100000;

	public static final int ITEM_WITH_CODE_ALREADY_EXIST = 100001;

	public static final int ITEM_TEMPLATE_DISABLED = 100002;

	public static final int ITEM_TEMPLATE_CODE_NOT_EXIST = 100003;

	public ItemException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public ItemException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public ItemException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static ItemException itemWithCodeNotExist(String itemCode) {
		return new ItemException(ITEM_WITH_CODE_NOT_EXIST, "Item ID=%s does not exist.", itemCode);
	}

	public static ItemException itemWithCodeAlreadyExist(String itemCode) {
		return new ItemException(ITEM_WITH_CODE_ALREADY_EXIST, "Item Code=%s already exist.", itemCode);
	}

	public static ItemException itemTemplateDisabled(String itemCode) {
		return new ItemException(ITEM_TEMPLATE_DISABLED, "ItemTemplate Code=%s is disabled.", itemCode);
	}

	public static ItemException itemTemplateCodeNotExist(String itemCode) {
		return new ItemException(ITEM_TEMPLATE_CODE_NOT_EXIST, "ItemTemplate Code=%s does not exist.", itemCode);
	}
}

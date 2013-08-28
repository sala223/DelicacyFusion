package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class ItemException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "Item";

    public static final int ITEM_WITH_ID_NOT_EXIST = 100000;

    public static final int ITEM_WITH_CODE_ALREADY_EXIST = 100001;

    public static final int ITEM_TEMPLATE_DISABLED = 100002;

    public ItemException(Throwable cause, int errorCode) {
	super(cause, REALM, errorCode);
    }

    public ItemException(Throwable cause, int errorCode, String messageFormat, Object... args) {
	super(cause, REALM, errorCode, messageFormat, args);
    }

    public ItemException(int errorCode, String messageFormat, Object... args) {
	super(null, REALM, errorCode, messageFormat, args);
    }

    public static ItemException itemWithIdNotExist(long itemId) {
	return new ItemException(ITEM_WITH_ID_NOT_EXIST, "Item ID=%s does not exist.", itemId);
    }

    public static ItemException itemWithCodeAlreadyExist(String code) {
	return new ItemException(ITEM_WITH_CODE_ALREADY_EXIST, "Item Code=%s already exist.", code);
    }

    public static ItemException itemTemplateDisabled(String code) {
	return new ItemException(ITEM_TEMPLATE_DISABLED, "ItemTemplate Code=%s is disabled.", code);
    }
}

package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class DiningTableException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "DiningTable";

    public static final int TABLE_WITH_NUMBER_EXIST = 100000;

    public static final int TABLE_WITH_BAR_CODE_EXIST = 100001;

    public static final int TABLE_WITH_ID_NOT_EXIST = 100002;

    public DiningTableException(Throwable cause, int errorCode) {
	super(cause, REALM, errorCode);
    }

    public DiningTableException(Throwable cause, int errorCode, String messageFormat, Object... args) {
	super(cause, REALM, errorCode, messageFormat, args);
    }

    public DiningTableException(int errorCode, String messageFormat, Object... args) {
	super(null, REALM, errorCode, messageFormat, args);
    }

    public static DiningTableException tableWithNumberAlreadyExist(String number) {
	return new DiningTableException(TABLE_WITH_NUMBER_EXIST, "Table number=%s is already exist.", number);
    }

    public static DiningTableException tableWithBarCodeAlreadyExist(String barCode) {
	return new DiningTableException(TABLE_WITH_BAR_CODE_EXIST, "Table barCode=%s is already exist.", barCode);
    }

    public static DiningTableException tableWithIdNotExist(long tableId) {
	return new DiningTableException(TABLE_WITH_ID_NOT_EXIST, "Table id=%s does not exist.", tableId);
    }
}

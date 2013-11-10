package com.df.http.masterdata.resources.exception;

import com.df.core.common.exception.BusinessException;

public class InputValidationException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "inputValidation";

    public InputValidationException(int errorCode, String messageFormat, Object... args) {
	super(null, REALM, errorCode, messageFormat, args);
    }
}

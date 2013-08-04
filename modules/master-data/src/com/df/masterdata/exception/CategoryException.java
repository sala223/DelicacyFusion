package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class CategoryException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "Category";

    public static final int PARENT_CATEGORY_NOT_FOUND = 100000;

    public CategoryException(Throwable cause, int errorCode) {
	super(cause, REALM, errorCode);
    }

    public CategoryException(Throwable cause, int errorCode, String messageFormat, Object... args) {
	super(cause, REALM, errorCode, messageFormat, args);
    }

    public CategoryException(int errorCode, String messageFormat, Object... args) {
	super(null, REALM, errorCode, messageFormat, args);
    }

    public static CategoryException parentCategoryNotFound(long parentCategoryId) {
	return new CategoryException(PARENT_CATEGORY_NOT_FOUND, "Category ID=%s is not found.", parentCategoryId);
    }

}

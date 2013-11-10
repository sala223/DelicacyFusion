package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class CategoryException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "Category";

    public static final int UNREMOVEABLE_ITEM_TEMPLATE_LIST_NOT_EMPTY = 100001;

    public static final int CATEGORY_WITH_CODE_EXIST = 100002;

    public static final int EXCEED_MAX_CATEGORY_COUNT = 100003;

    public static final int NON_EXISTING_CATEGORY_CODE = 100004;

    public CategoryException(Throwable cause, int errorCode) {
	super(cause, REALM, errorCode);
    }

    public CategoryException(Throwable cause, int errorCode, String messageFormat, Object... args) {
	super(cause, REALM, errorCode, messageFormat, args);
    }

    public CategoryException(int errorCode, String messageFormat, Object... args) {
	super(null, REALM, errorCode, messageFormat, args);
    }

    public static CategoryException itemTemplateListNotEmpty(String categoryCode) {
	String msg = "Cannot remove category ID=%s, there are item template belongs to this category.";
	return new CategoryException(UNREMOVEABLE_ITEM_TEMPLATE_LIST_NOT_EMPTY, msg, categoryCode);
    }

    public static CategoryException categoryWithCodeExist(String categoryCode) {
	String msg = "Category Code=%s, already exsit.";
	return new CategoryException(CATEGORY_WITH_CODE_EXIST, msg, categoryCode);
    }

    public static CategoryException exceedMaxCategoryCount(int maxCategoryCount) {
	String msg = "Exceed max category=%d count limitation.";
	return new CategoryException(EXCEED_MAX_CATEGORY_COUNT, msg, maxCategoryCount);
    }

    public static CategoryException nonExistingCategoryCode(String categoryCode) {
	String msg = "Invalid category code %s, category code must be predefined.";
	return new CategoryException(NON_EXISTING_CATEGORY_CODE, msg, categoryCode);
    }
}

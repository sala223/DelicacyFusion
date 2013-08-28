package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class CategoryException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "Category";

    public static final int PARENT_CATEGORY_NOT_FOUND = 100000;

    public static final int UNREMOVEABLE_ITEM_TEMPLATE_LIST_NOT_EMPTY = 100001;

    public static final int UNREMOVEABLE_DESCENDANTS_EXIST = 100002;

    public static final int CATEGORY_WITH_NAME_EXIST = 100003;

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

    public static CategoryException itemTemplateListNotEmpty(long categoryId) {
	String msg = "Cannot remove category ID=%s, there are item template belongs to this category.";
	return new CategoryException(UNREMOVEABLE_ITEM_TEMPLATE_LIST_NOT_EMPTY, msg, categoryId);
    }

    public static CategoryException descendantsExist(long categoryId) {
	String msg = "Cannot remove category ID=%s, there are descendants belongs to this category.";
	return new CategoryException(UNREMOVEABLE_DESCENDANTS_EXIST, msg, categoryId);
    }

    public static CategoryException categoryWithNameExist(String categoryName) {
	String msg = "Category Name=%s, already exsit.";
	return new CategoryException(CATEGORY_WITH_NAME_EXIST, msg, categoryName);
    }
}

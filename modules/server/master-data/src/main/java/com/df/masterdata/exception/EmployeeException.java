package com.df.masterdata.exception;

import com.df.core.common.exception.BusinessException;

public class EmployeeException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "Employee";

	public static final int EMAIL_IS_OCCUPIED = 100000;

	public static final int CELL_PHONE_IS_OCCUPIED = 100001;

	public static final int EMPLYEE_WITH_EMAIL_NOT_FOUND = 100002;

	public EmployeeException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public EmployeeException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public EmployeeException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static EmployeeException emailIsOccupied(String email) {
		return new EmployeeException(EMAIL_IS_OCCUPIED, "Email %s is occupied.", email);
	}

	public static EmployeeException cellphoneIsOccupied(String cellphone) {
		return new EmployeeException(CELL_PHONE_IS_OCCUPIED, "Cellphone %s is occupied.", cellphone);
	}

	public static EmployeeException employeeWithEmailNotFound(String email) {
		return new EmployeeException(EMPLYEE_WITH_EMAIL_NOT_FOUND, "Employee is not found with email %s", email);
	}
}

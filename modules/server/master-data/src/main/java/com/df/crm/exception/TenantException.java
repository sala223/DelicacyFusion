package com.df.crm.exception;

import com.df.core.common.exception.BusinessException;

public class TenantException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "Tenant";

	public static final int TENANT_WITH_NAME_ALREADY_EXIST = 100000;

	public static final int TENANT_WITH_CODE_ALREADY_EXIST = 100001;

	public static final int TENANT_WITH_CODE_NOT_FOUND = 100002;

	public static final int TENANT_WITH_NAME_NOT_FOUND = 100003;

	public static final int TENANT_OWNER_NOT_FOUND = 100004;

	public static final int EXCEED_TENANT_CREATE_LIMITATION = 100005;

	public TenantException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public TenantException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public TenantException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static TenantException tenantWithNameAlreadyExist(String name) {
		return new TenantException(TENANT_WITH_NAME_ALREADY_EXIST, "Tenant name=%s is already exist.", name);
	}

	public static TenantException tenantWithCodeAlreadyExist(String code) {
		return new TenantException(TENANT_WITH_CODE_ALREADY_EXIST, "Tenant code=%s is already exist.", code);
	}

	public static TenantException tenantWithCodeNotFound(String code) {
		return new TenantException(TENANT_WITH_CODE_NOT_FOUND, "Tenant with code=%s is not found", code);
	}

	public static TenantException tenantWithNameNotFound(String name) {
		return new TenantException(TENANT_WITH_NAME_NOT_FOUND, "Tenant with name=%s is not found", name);
	}

	public static TenantException tenantOwnerNotFound(long ownerId) {
		return new TenantException(TENANT_OWNER_NOT_FOUND, "Tenant with owner = %s is not found", ownerId);
	}

	public static TenantException exceedTenantCreateLimitation() {
		return new TenantException(EXCEED_TENANT_CREATE_LIMITATION, "Exceed tenant creation limitation");
	}
}

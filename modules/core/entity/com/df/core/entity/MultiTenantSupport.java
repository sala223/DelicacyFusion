package com.df.core.entity;

import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;
import org.eclipse.persistence.annotations.MultitenantType;

@MappedSuperclass
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = MultiTenantSupport.TENANT_COLUMN, length = 12, contextProperty = MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY)
public class MultiTenantSupport {

	public static final String MULTITENANT_CONTEXT_PROPERTY = "tenant.id";

	public static final String TENANT_COLUMN = "TENANT_ID";

}

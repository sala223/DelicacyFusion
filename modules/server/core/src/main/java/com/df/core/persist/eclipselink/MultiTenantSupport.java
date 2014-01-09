package com.df.core.persist.eclipselink;

import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

@MappedSuperclass
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = MultiTenantSupport.TENANT_COLUMN, length = 12, contextProperty = MultiTenantSupport.MULTITENANT_CONTEXT_PROPERTY)
public abstract class MultiTenantSupport {

    public static final String MULTITENANT_CONTEXT_PROPERTY = "tenant.id";

    public static final String TENANT_COLUMN = "TENANT_CODE";

}

package com.df.core.persist.testsuit;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import com.df.core.common.context.TenantContext;
import com.df.core.common.context.TenantContextHolder;

public class TenantContextInjectionTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
	super.beforeTestClass(testContext);
	TenantContextHolder.setTenant(new TenantContext("test"));
    }

}

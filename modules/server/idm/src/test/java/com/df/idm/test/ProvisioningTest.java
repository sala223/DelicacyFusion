package com.df.idm.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.df.core.provision.EntityDataProvisioningBean;
import com.df.core.provision.ProvisioningContext;
import com.df.core.provision.ProvisioningContext.ProvisioningStatus;

public class ProvisioningTest extends IdmBaseTest {

	@Autowired
	private EntityDataProvisioningBean provisioningBean;

	@Test
	public void testProvisioningIdmData() throws Throwable {
		ProvisioningContext context = new ProvisioningContext();
		provisioningBean.execute(context);
		if (context.hasError()) {
			ProvisioningStatus status = context.getBeanRunningStatus("idmDataProvisioningBean");
			throw status.getError();
		}
		TestCase.assertFalse(context.hasError());
	}
}

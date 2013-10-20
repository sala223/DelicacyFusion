package com.df.client.rs.resource.test;

import org.junit.BeforeClass;

import com.df.client.http.DFClient;
import com.df.client.http.LoginContext;
import com.df.client.http.ResourceContext;

public abstract class AbstractResourceTest {

    private static DFClient client;

    @BeforeClass
    public static void setup() {
	LoginContext lc = new LoginContext("test", "test");
	client = new DFClient(lc);
	ResourceContext rc = new ResourceContext();
	rc.setUserCode("test");
	rc.setTenantCode("test");
	rc.setStoreCode("s1");
	rc.setTargetUrl("http://localhost:8080/m-console/rs");
	client.setResourceContext(rc);
    }

    protected DFClient getClient() {
	return client;
    }
}

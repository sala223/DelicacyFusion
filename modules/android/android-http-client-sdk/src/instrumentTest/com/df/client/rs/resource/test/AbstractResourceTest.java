package com.df.client.rs.resource.test;

import junit.framework.TestCase;

import com.df.client.http.DFClient;
import com.df.client.http.LoginContext;

public abstract class AbstractResourceTest extends TestCase {

    private static DFClient client;

    public void setUp() {
	if (client == null) {
	    LoginContext lc = new LoginContext("test", "testuser", "testuserpassword");
	    client = new DFClient(lc, "http://10.59.149.156:8080/m-console");
	    client.login();
	    client.setStore("S1");
	}
    }

    public void tearDown() {

    }

    protected DFClient getClient() {
	return client;
    }
}

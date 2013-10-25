package com.df.client.rs.resource.test;

import junit.framework.TestCase;
import android.util.Log;

import com.df.client.http.DFClient;
import com.df.client.http.LoginContext;

public abstract class AbstractResourceTest extends TestCase {

    private static DFClient client;

    public void setUp() {
	if (client == null) {
	    LoginContext lc = new LoginContext("test", "testuser", "testuserpassword");
	    client = new DFClient(lc, "http://10.0.2.2:8080/m-console");
	    client.login();
	    client.setStore("S1");
	}
    }

    public void tearDown() {

    }

    protected void logError(Throwable ex, String error) {
	Log.e(this.getClass().getName(), error, ex);
    }

    protected void logInfo(String message) {
	Log.i(this.getClass().getName(), message);
    }

    protected DFClient getClient() {
	return client;
    }
}

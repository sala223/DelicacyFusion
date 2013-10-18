package com.df.client.test;

import org.junit.BeforeClass;

import com.df.client.DFClient;
import com.df.client.LoginContext;

public abstract class ClientTest {

    private static DFClient client;

    private static final String targetUrl = "http://127.0.0.1:8080/masterdata/rs";

    @BeforeClass
    public static void init() {
	LoginContext loginContext = new LoginContext("test", "test_user");
	client = new DFClient(loginContext, targetUrl);
    }

    protected DFClient getClient() {
	return client;
    }
}

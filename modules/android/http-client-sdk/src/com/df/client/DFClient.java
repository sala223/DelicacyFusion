package com.df.client;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.df.client.rs.resource.Resource;

public class DFClient {

    private LoginContext loginContext;

    private String targetUrl;

    ResteasyClient client;

    ResteasyWebTarget target;

    private ConcurrentHashMap<Class<? extends Resource>, SoftReference<? extends Resource>> cache;

    public boolean authenticate() {
	loginContext.getUserCode();
	return true;
    }

    public DFClient(LoginContext loginContext, String targetUrl) {
	this.loginContext = loginContext;
	this.targetUrl = targetUrl;
	this.cache = new ConcurrentHashMap<Class<? extends Resource>, SoftReference<? extends Resource>>();
	this.client = new ResteasyClientBuilder().build();
	this.target = client.target(this.targetUrl);
    }

    private <T extends Resource> T getResourceFromCache(Class<T> resourceType) {
	@SuppressWarnings("unchecked")
	SoftReference<T> reference = (SoftReference<T>) cache.get(resourceType);
	if (reference != null) {
	    return reference.get();
	}
	return null;
    }

    public <T extends Resource> T getResource(Class<T> resourceType) {
	T rs = getResourceFromCache(resourceType);
	if (rs == null) {
	    rs = this.target.proxy(resourceType);
	    this.cache.put(resourceType, new SoftReference<T>(rs));
	}
	return rs;
    }
}

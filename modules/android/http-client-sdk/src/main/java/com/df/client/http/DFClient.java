package com.df.client.http;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import com.df.client.http.factory.ResourceFactory;
import com.df.client.http.factory.ResourceFactoryImpl;
import com.df.client.rs.resource.Resource;

public class DFClient {

    private LoginContext loginContext;

    private ConcurrentHashMap<Class<? extends Resource>, SoftReference<? extends Resource>> cache;

    private ResourceFactory resourceFactory;

    public boolean authenticate() {
	this.loginContext.getUserCode();
	return true;
    }

    public DFClient(LoginContext loginContext) {
	this.loginContext = loginContext;
	this.cache = new ConcurrentHashMap<Class<? extends Resource>, SoftReference<? extends Resource>>();
    }

    public void setResourceContext(ResourceContext resourceContext) {
	cache.clear();
	resourceFactory = new ResourceFactoryImpl(resourceContext);
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
	if (this.resourceFactory == null) {
	    throw new IllegalStateException("ResourceContext is not initialized.");
	}
	T res = this.getResourceFromCache(resourceType);
	if (res == null) {
	    res = this.resourceFactory.getResource(resourceType);
	    if (res != null) {
		this.cache.put(resourceType, new SoftReference<T>(res));
	    }
	}
	return res;
    }
}

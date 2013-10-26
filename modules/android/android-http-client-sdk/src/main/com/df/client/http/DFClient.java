package com.df.client.http;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import com.df.client.android.http.factory.ResourceFactoryImpl;
import com.df.client.rs.resource.Resource;

public class DFClient {

    private LoginContext loginContext;

    private ConcurrentHashMap<Class<? extends Resource>, SoftReference<? extends Resource>> cache;

    private ResourceFactory resourceFactory;

    private String targetUrl;

    private ResourceContext rc;

    private static final String RESOURCE_URL_PREFIX = "/rs";

    public void login() {
	this.loginContext.login();
	rc = new ResourceContext();
	rc.setTargetUrl(targetUrl + RESOURCE_URL_PREFIX);
	rc.setUserCode(this.loginContext.getUserCode());
	rc.setTenantCode(this.loginContext.getTenantCode());
	resourceFactory = new ResourceFactoryImpl(rc);
    }

    public void setStore(String storeCode) {
	rc.setStoreCode(storeCode);
    }
    
    public String getStoreCode() {
    	return rc.getStoreCode();
    }

    public String[] getAvaliableStores() {
	return null;
    }

    public DFClient(LoginContext loginContext, String targetUrl) {
	this.loginContext = loginContext;
	this.targetUrl = targetUrl;
	this.cache = new ConcurrentHashMap<Class<? extends Resource>, SoftReference<? extends Resource>>();
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
	    res.setContext(rc);
	    if (res != null) {
		this.cache.put(resourceType, new SoftReference<T>(res));
	    }
	}
	return res;
    }

    public void close() {
	loginContext.logout();
    }
}

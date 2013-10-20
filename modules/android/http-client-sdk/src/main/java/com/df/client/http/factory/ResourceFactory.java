package com.df.client.http.factory;

import com.df.client.http.ResourceContext;
import com.df.client.rs.resource.Resource;

public abstract class ResourceFactory {

    private ResourceContext resourceContext;

    public ResourceFactory(ResourceContext resourceContext) {
	this.resourceContext = resourceContext;
    }

    protected ResourceContext getResourceContext() {
	return resourceContext;
    }

    public abstract <T extends Resource> T getResource(Class<T> resourceType);

}

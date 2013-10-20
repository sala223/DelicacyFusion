package com.df.client.rs.resource;

import com.df.client.http.ResourceContext;

public abstract class AbstractResource implements Resource {

    private ResourceContext resourceContext;

    @Override
    public void setContext(ResourceContext resourceContext) {

	this.resourceContext = resourceContext;
    }

    protected ResourceContext getResourceContext() {
	return resourceContext;
    }
}

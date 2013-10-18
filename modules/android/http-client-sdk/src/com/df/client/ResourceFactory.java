package com.df.client;

import com.df.client.rs.resource.Resource;

public interface ResourceFactory {

    public <T extends Resource> T getResource(Class<T> resourceType);

    public void setTargetUrl(String targetUrl);
}

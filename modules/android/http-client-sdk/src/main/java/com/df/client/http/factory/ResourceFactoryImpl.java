package com.df.client.http.factory;

import java.util.HashMap;
import java.util.Map;

import com.df.client.http.ResourceContext;
import com.df.client.rs.resource.CategoryResource;
import com.df.client.rs.resource.ItemResource;
import com.df.client.rs.resource.Resource;
import com.df.client.rs.resource.exception.ResourceInstantiateError;
import com.df.client.rs.resource.impl.srt.CategoryResourceImpl;
import com.df.client.rs.resource.impl.srt.ItemResourceImpl;

public class ResourceFactoryImpl extends ResourceFactory {

    private ResourceContext resourceContext;

    private static Map<Class<? extends Resource>, Class<? extends Resource>> typeMapping;

    static {
	typeMapping = new HashMap<Class<? extends Resource>, Class<? extends Resource>>();
	typeMapping.put(CategoryResource.class, CategoryResourceImpl.class);
	typeMapping.put(ItemResource.class, ItemResourceImpl.class);
    }

    public static <T extends Resource> void registerResourceType(Class<T> resourceType, Class<T> implType) {
	typeMapping.put(resourceType, implType);
    }

    public ResourceFactoryImpl(ResourceContext resourceContext) {
	super(resourceContext);
    }

    @Override
    public <T extends Resource> T getResource(Class<T> resourceType) {
	try {
	    Class<? extends Resource> implType = typeMapping.get(resourceType);
	    if (implType == null) {
		throw new ResourceInstantiateError(null, "Resource type %s is not registered");
	    }
	    @SuppressWarnings("unchecked")
	    T inst = (T) implType.newInstance();
	    inst.setContext(resourceContext);
	    return inst;
	} catch (InstantiationException ex) {
	    throw new ResourceInstantiateError(ex);
	} catch (IllegalAccessException ex) {
	    throw new ResourceInstantiateError(ex);
	}
    }
}

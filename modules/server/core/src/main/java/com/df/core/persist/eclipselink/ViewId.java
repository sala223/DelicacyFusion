package com.df.core.persist.eclipselink;

import java.io.Serializable;

public class ViewId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String viewName;

    private Class<?> entityType;

    public ViewId(String viewName, Class<?> entityType) {
	this.viewName = viewName;
	this.entityType = entityType;
    }
    
    public String getViewName() {
	return viewName;
    }

    public Class<?> getEntityType() {
	return entityType;
    }
}

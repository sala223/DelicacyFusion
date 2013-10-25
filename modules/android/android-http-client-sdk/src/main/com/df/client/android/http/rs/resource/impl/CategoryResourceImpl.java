package com.df.client.android.http.rs.resource.impl;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Category;
import com.df.client.rs.resource.CategoryResource;

public class CategoryResourceImpl extends RestTemplateResource implements CategoryResource {

    @Override
    public Category[] getCategories() {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/category";
	return this.getRestTemplate().getForObject(url, Category[].class, rc.getTenantCode());
    }

}

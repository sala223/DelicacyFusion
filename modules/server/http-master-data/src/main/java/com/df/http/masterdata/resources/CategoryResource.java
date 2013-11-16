package com.df.http.masterdata.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.auxiliary.Category;
import com.df.masterdata.auxiliary.CategoryLoader;

@Path("/tenant/{tenantCode}/category/")
@Produces("application/json;charset=UTF-8")
@Component
public class CategoryResource extends TenantResource {

	@Inject
	private CategoryLoader categoryLoader;

	public void setCategoryLoader(CategoryLoader categoryLoader) {
		this.categoryLoader = categoryLoader;
	}

	/**
	 * Get all categories in a tenant
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @return a list of category in a tenant.
	 */
	@GET
	@Path("/")
	@TypeHint(Category.class)
	public Category[] getCategories(@PathParam("tenantCode") String tenantCode) {
		injectTenantContext(tenantCode);
		return categoryLoader.loadCategories();
	}

}

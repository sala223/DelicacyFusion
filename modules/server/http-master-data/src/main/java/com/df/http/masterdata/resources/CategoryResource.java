package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Category;
import com.df.masterdata.service.contract.CategoryServiceInf;

@Path("/tenant/{tenantCode}/category/")
@Produces("application/json;charset=UTF-8")
@Component
public class CategoryResource extends TenantResource {

    @Inject
    @Qualifier("resourceBundleCategoryService")
    private CategoryServiceInf categoryService;

    public void setCategoryService(CategoryServiceInf categoryService) {
	this.categoryService = categoryService;
    }

    /**
     * Get all categories in a tenant
     * 
     * @param tenantCode
     *            The tenant code
     * @return all the categories
     */
    @GET
    @Path("/")
    public List<Category> getCategories(@PathParam("tenantCode") String tenantCode) {
	injectTenantContext(tenantCode);
	return categoryService.getCategories();
    }

}

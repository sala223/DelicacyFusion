package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.auxiliary.template.CategoryProfile;
import com.df.masterdata.auxiliary.template.CategoryTemplate;
import com.df.masterdata.entity.Category;
import com.df.masterdata.service.contract.CategoryServiceInf;

@Path("/tenant/{tenantCode}/category/")
@Produces("application/json;charset=UTF-8")
@Component
public class CategoryResource extends TenantResource {

    @Inject
    private CategoryServiceInf categoryService;

    @Inject
    private CategoryTemplate categoryTemplate;

    public void setCategoryService(CategoryServiceInf categoryService) {
	this.categoryService = categoryService;
    }

    /**
     * Get a list of category in a tenant
     * 
     * @param tenantCode
     *            The tenant code
     * @return all the categories
     */
    @GET
    @Path("/")
    @TypeHint(Category.class)
    public List<Category> getCategories(@PathParam("tenantCode") String tenantCode) {
	injectTenantContext(tenantCode);
	return categoryService.getCategories();
    }

    /**
     * Get a list of predefined category profile from template.
     * 
     * @param tenantCode
     *            The tenant code
     * @return the predefined category list.
     */
    @GET
    @Path("/template")
    @TypeHint(Category.class)
    public CategoryProfile[] getCategoriesFromTemplate(@PathParam("tenantCode") String tenantCode) {
	return categoryTemplate.getCategories();
    }

    /**
     * Add the specified categories which come from category template for a
     * tenant. <br>
     * category template is global visible to all tenants and tenant is only
     * allowed to add the predefined categories.
     * 
     * @param tenantCode
     *            The tenant code
     * @param categoryCodes
     *            the category codes to be enabled.
     */
    @POST
    @Path("/template/add")
    public void addCategoryFromTemplate(@PathParam("tenantCode") String tenantCode,
	    @QueryParam("code") String[] categoryCodes) {
	categoryService.newCategoryFromTemplate(categoryCodes);
    }

    /**
     * Remove a specified category for a tenant.
     * 
     * @param tenantCode
     *            The tenant code
     * @param categoryCode
     *            the category code to be removed.
     */
    @DELETE
    @Path("/template/remove")
    public void removeCategory(@PathParam("tenantCode") String tenantCode, @QueryParam("code") String categoryCode) {
	categoryService.removeCategory(categoryCode);
    }
}

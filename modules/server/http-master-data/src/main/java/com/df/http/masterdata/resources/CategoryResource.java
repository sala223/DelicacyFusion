package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.core.rs.resteasy.StringArrayUnmarsahller.StringArray;
import com.df.masterdata.auxiliary.template.CategoryProfile;
import com.df.masterdata.auxiliary.template.CategoryTemplate;
import com.df.masterdata.entity.Category;
import com.df.masterdata.service.contract.CategoryService;

@Path("/tenant/{tenantCode}/category/")
@Produces("application/json;charset=UTF-8")
@Component
public class CategoryResource extends TenantResource {

    @Inject
    private CategoryService categoryService;

    @Inject
    private CategoryTemplate categoryTemplate;

    public void setCategoryService(CategoryService categoryService) {
	this.categoryService = categoryService;
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
    @TypeHint(CategoryProfile.class)
    public CategoryProfile[] getCategoriesFromTemplate(@PathParam("tenantCode") String tenantCode) {
	return categoryTemplate.getCategories();
    }

    /**
     * Add categories for a tenant. <br>
     * Tenant is only allowed to select categories from category template and
     * add them to its own categories.
     * 
     * @param tenantCode
     *            The tenant code
     * @param categoryCodes
     *            the category codes to be added. Multiple category code must be
     *            separated with ",".
     */
    @POST
    @Path("/codes={categoryCodes}")
    public void addCategoryFromTemplate(@PathParam("tenantCode") String tenantCode,
	    @PathParam("categoryCodes") @StringArray String[] categoryCodes) {
	categoryService.newCategoryFromTemplate(categoryCodes);
    }

    /**
     * Remove a specified category from a tenant.
     * 
     * @param tenantCode
     *            The tenant code
     * @param categoryCode
     *            the category code to be removed.
     */
    @DELETE
    @Path("/{categoryCode}")
    public void removeCategory(@PathParam("tenantCode") String tenantCode,
	    @PathParam("categoryCode") String categoryCode) {
	categoryService.removeCategory(categoryCode);
    }
}

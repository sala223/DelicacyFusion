package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.df.core.common.context.TenantContext;
import com.df.core.common.context.TenantContextHolder;
import com.df.masterdata.entity.Category;
import com.df.masterdata.service.inf.CategoryServiceInf;

@Path("/{tenantId}/category/")
@Produces("application/json")
@Component
public class CategoryResource {

    @Inject
    private CategoryServiceInf categoryService;

    public void setCategoryService(CategoryServiceInf categoryService) {
	this.categoryService = categoryService;
    }

    @GET
    @Path("/")
    public List<Category> getCategories(@PathParam("tenantId") String tenantId) {
	injectTenantContext(tenantId);
	return categoryService.getRootCategories();
    }

    @DELETE
    @Path("/{categoryId}/delete")
    public void removeCategory(@PathParam("tenantId") String tenantId, @PathParam("categoryId") long categoryId) {
	injectTenantContext(tenantId);
	categoryService.removeCategory(categoryId);
    }

    private void injectTenantContext(String tenantId) {
	TenantContext context = new TenantContext(tenantId);
	TenantContextHolder.setTenant(context);
    }
}

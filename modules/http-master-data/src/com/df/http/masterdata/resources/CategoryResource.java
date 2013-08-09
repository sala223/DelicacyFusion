package com.df.http.masterdata.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.Item;
import com.df.masterdata.service.inf.CategoryServiceInf;

@Path("/categories/")
@Produces("application/json")
public class CategoryResource {

    @Autowired
    private CategoryServiceInf categoryService;

    @GET
    @Path("/{categoryId}/foods")
    public List<Item> getFoodsByCategory(@PathParam("categoryId") long categoryId) {
	return null;
    }

    @GET
    @Path("/")
    public List<Category> getCategories() {
	return categoryService.getRootCategories();
    }
}

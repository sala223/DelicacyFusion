package com.df.client.rs.resource.test;

import android.test.suitebuilder.annotation.MediumTest;

import com.df.client.rs.model.Category;
import com.df.client.rs.resource.CategoryResource;
import com.google.gson.Gson;

public class CategoryResourceTest extends AbstractResourceTest {

    @MediumTest
    public void testGetCategories() {
	CategoryResource categoryResource = this.getClient().getResource(CategoryResource.class);
	Category[] categories = categoryResource.getCategories();
	Gson gson = new Gson();
	this.logInfo(gson.toJson(categories));
    }
}

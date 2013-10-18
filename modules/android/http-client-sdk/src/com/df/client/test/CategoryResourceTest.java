package com.df.client.test;

import org.junit.Test;

import com.df.client.rs.resource.CategoryResource;

public class CategoryResourceTest extends ClientTest{

    @Test
    public void testGetCategories(){
	CategoryResource resource = this.getClient().getResource(CategoryResource.class);
	resource.getCategories("TEST");
    }
}

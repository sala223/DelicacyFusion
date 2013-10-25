package com.df.client.rs.resource;

import java.util.List;

import com.df.client.rs.model.Category;

public interface CategoryResource extends Resource {

    public List<Category> getCategories();

}

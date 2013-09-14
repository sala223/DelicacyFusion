package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.Category;

public interface CategoryServiceInf {

    public List<Category> getCategories();

    public void newCategory(Category category);
    
    public void removeCategory(String categoryCode);

}

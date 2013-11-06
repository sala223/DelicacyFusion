package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.Category;

public interface CategoryServiceInf {

    List<Category> getCategories();

    void newCategory(Category category);
    
    void newCategoryFromTemplate(String... categoryCodes);

    void removeCategory(String categoryCode);

}

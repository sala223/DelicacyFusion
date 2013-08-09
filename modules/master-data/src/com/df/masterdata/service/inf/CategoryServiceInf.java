package com.df.masterdata.service.inf;

import java.util.List;

import com.df.masterdata.entity.Category;

public interface CategoryServiceInf {

    List<Category> getRootCategories();

    int removeCategory(long categoryId);

    void newCategory(Category c, Long parentCategoryId);

}

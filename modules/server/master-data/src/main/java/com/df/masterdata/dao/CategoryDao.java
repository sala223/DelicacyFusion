package com.df.masterdata.dao;

import java.util.List;


import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.Constants.CATEGORY;
import com.df.masterdata.exception.CategoryException;

public class CategoryDao extends MasterDataAccessFoundation {

    public List<Category> all() {
	return this.all(Category.class, 0, Integer.MAX_VALUE, false);
    }

    public Category findCategoryByCode(String categoryCode) {
	return findSingleEntityByProperty(Category.class, CATEGORY.CODE_PROPERTY, categoryCode);
    }

    public void removeCategoryByCode(String categoryCode) {
	Category found = this.find(Category.class, categoryCode);
	if (found == null) {
	    return;
	} else {
	    this.remove(found);
	}
    }

    public void newCategory(Category c) {
	if (this.findCategoryByCode(c.getCode()) != null) {
	    throw CategoryException.categoryWithCodeExist(c.getCode());
	}
	insert(c);
    }

}

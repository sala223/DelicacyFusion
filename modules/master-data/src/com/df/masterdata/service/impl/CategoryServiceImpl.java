package com.df.masterdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.entity.Category;
import com.df.masterdata.exception.CategoryException;
import com.df.masterdata.service.inf.CategoryServiceInf;

public class CategoryServiceImpl implements CategoryServiceInf {

    @Autowired
    private CategoryDAL categoryDAL;

    public void setCategoryDAL(CategoryDAL categoryDAL) {
	this.categoryDAL = categoryDAL;
    }

    @Override
    public List<Category> getRootCategories() {
	return categoryDAL.getRootCategories();
    }

    @Override
    public int removeCategory(long categoryId) {
	return categoryDAL.removeCategoryById(categoryId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT)
    public void newCategory(Category c, Long parentCategoryId) {
	if (parentCategoryId != null) {
	    Category parent = categoryDAL.find(Category.class, parentCategoryId);
	    if (parent != null) {
		c.setParent(parent);
	    } else {
		throw CategoryException.parentCategoryNotFound(parentCategoryId);
	    }
	}
	categoryDAL.insert(c);
    }

}
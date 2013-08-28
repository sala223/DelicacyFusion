package com.df.masterdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.dal.ItemTemplateDAL;
import com.df.masterdata.entity.Category;
import com.df.masterdata.service.inf.CategoryServiceInf;

@Transactional
public class CategoryServiceImpl implements CategoryServiceInf {

    @Autowired
    private CategoryDAL categoryDAL;

    @Autowired
    private ItemTemplateDAL itemTemplateDAL;

    public void setCategoryDAL(CategoryDAL categoryDAL) {
	this.categoryDAL = categoryDAL;
    }

    @Override
    public List<Category> getRootCategories() {
	return categoryDAL.getRootCategories();
    }

    @Override
    public void removeCategory(long categoryId) {
	categoryDAL.removeCategoryById(categoryId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT)
    public void newCategory(Category c, Long parentCategoryId) {
	categoryDAL.newCategory(c, parentCategoryId);
    }

}

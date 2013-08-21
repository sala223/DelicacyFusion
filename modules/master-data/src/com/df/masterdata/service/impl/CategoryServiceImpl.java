package com.df.masterdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.CategoryDAL;
import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.Item;
import com.df.masterdata.exception.CategoryException;
import com.df.masterdata.service.inf.CategoryServiceInf;
import com.df.masterdata.service.inf.ItemServiceInf;

@Transactional
public class CategoryServiceImpl implements CategoryServiceInf {

    @Autowired
    private CategoryDAL categoryDAL;

    @Autowired
    private ItemServiceInf itemService;

    public void setCategoryDAL(CategoryDAL categoryDAL) {
	this.categoryDAL = categoryDAL;
    }

    @Override
    public List<Category> getRootCategories() {
	return categoryDAL.getRootCategories();
    }

    @Override
    public void removeCategory(long categoryId) {
	List<Item> items = itemService.getItemsByCategory(categoryId);
	if (items.size() > 0) {
	    throw CategoryException.itemListNotEmpty(categoryId);
	}
	categoryDAL.removeCategoryById(categoryId);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT)
    public void newCategory(Category c, Long parentCategoryId) {
	categoryDAL.newCategory(c, parentCategoryId);
    }

}

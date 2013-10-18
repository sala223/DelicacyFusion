package com.df.masterdata.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.CategoryDao;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.exception.CategoryException;
import com.df.masterdata.service.contract.CategoryServiceInf;

@Transactional
public class CategoryServiceImpl implements CategoryServiceInf {

    @Autowired
    private CategoryDao categoryDao;
    
    @Inject
    private ItemTemplateDao itemTemplateDao;

    private int maxCategoryCount = 10;

    public void setMaxCategoryCount(int maxCategoryCount) {
	this.maxCategoryCount = maxCategoryCount;
    }

    public void setCategoryDAL(CategoryDao categoryDAL) {
	this.categoryDao = categoryDAL;
    }

    public void setItemTemplateDAL(ItemTemplateDao itemTemplateDAL) {
        this.itemTemplateDao = itemTemplateDAL;
    }

    @Override
    public List<Category> getCategories() {
	return categoryDao.all();
    }

    @Override
    public void newCategory(Category category) {
	int allCount = categoryDao.allCount(Category.class, false);
	if (allCount >= maxCategoryCount) {
	    throw CategoryException.exceedMaxCategoryCount(maxCategoryCount);
	}
	Category found = categoryDao.findCategoryByCode(category.getCode()); 
	if(found != null){
	    throw CategoryException.categoryWithCodeExist(category.getCode());
	}
	categoryDao.insert(category); 
    }

    @Override
    public void removeCategory(String categoryCode) {
	List<ItemTemplate> itemTemplates = itemTemplateDao.getItemTemplateByCategory(categoryCode);
	if (itemTemplates.size() > 0) {
	    throw CategoryException.itemTemplateListNotEmpty(categoryCode);
	}
	categoryDao.removeCategoryByCode(categoryCode); 
    }

}

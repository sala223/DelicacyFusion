package com.df.masterdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.auxiliary.template.CategoryProfile;
import com.df.masterdata.auxiliary.template.CategoryTemplate;
import com.df.masterdata.dao.CategoryDao;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.exception.CategoryException;
import com.df.masterdata.service.contract.CategoryService;

@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ItemTemplateDao itemTemplateDao;

    @Autowired
    private CategoryTemplate categoryTemplate;

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

    public void setCategoryTemplate(CategoryTemplate categoryTemplate) {
	this.categoryTemplate = categoryTemplate;
    }

    @Override
    public List<Category> getCategories() {
	List<Category> categories = categoryDao.all();
	for (Category c : categories) {
	    CategoryProfile profile = categoryTemplate.getCategory(c.getCode());
	    c.setName(profile.getName());
	}
	return categories;
    }

    public void newCategoryFromTemplate(String... categoryCodes) {
	if (categoryCodes != null) {
	    for (String categoryCode : categoryCodes) {
		CategoryProfile profile = categoryTemplate.getCategory(categoryCode);
		if (profile == null) {
		    throw CategoryException.nonExistingCategoryCode(categoryCode);
		}
		Category c = new Category(profile);
		Category found = categoryDao.findCategoryByCode(c.getCode());
		if (found == null) {
		    newCategory(c);
		}
	    }
	}
    }

    @Override
    public void newCategory(Category category) {
	long allCount = categoryDao.allCount(Category.class, false);
	if (allCount >= maxCategoryCount) {
	    throw CategoryException.exceedMaxCategoryCount(maxCategoryCount);
	}
	Category found = categoryDao.findCategoryByCode(category.getCode());
	if (found != null) {
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

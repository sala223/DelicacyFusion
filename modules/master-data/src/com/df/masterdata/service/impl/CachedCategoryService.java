package com.df.masterdata.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

import com.df.core.common.utils.LocalCache;
import com.df.masterdata.entity.Category;
import com.df.masterdata.service.contract.CategoryServiceInf;

public class CachedCategoryService implements CategoryServiceInf {

    private static LocalCache<Locale, List<Category>> cache = new LocalCache<Locale, List<Category>>();

    private CategoryServiceInf service;

    public CachedCategoryService(CategoryServiceInf service) {
	this.service = service;
    }

    @Override
    public List<Category> getCategories() {
	Locale locale = LocaleContextHolder.getLocale();
	List<Category> tpls = cache.get(locale);
	if (tpls == null) {
	    synchronized (this) {
		tpls = service.getCategories();
		cache.put(locale, tpls);
	    }
	}
	return tpls;
    }

    @Override
    public void newCategory(Category category) {
	service.newCategory(category);
    }

    @Override
    public void removeCategory(String categoryCode) {
	service.removeCategory(categoryCode);
    }
}

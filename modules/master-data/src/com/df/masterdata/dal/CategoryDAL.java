package com.df.masterdata.dal;

import java.util.List;

import javax.persistence.TypedQuery;

import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.Constants.CATEGORY;

public class CategoryDAL extends MasterDataAccessFoundation {

    public List<Category> getRootCategories() {
	String entityName = this.getClassDescrptor(Category.class).getAlias();
	String eql = String.format("SELECT c FROM %s c WHERE c.parent IS NULL", entityName);
	TypedQuery<Category> query = this.getEntityManager().createQuery(eql, Category.class);
	return query.getResultList();
    }

    public Category findCategoryByName(String name) {
	return findSingleEntityByProperty(Category.class, CATEGORY.NAME_PROPERTY, name);
    }

    public int removeCategoryById(long categoryId) {
	return super.removeMasterDataById(Category.class, categoryId);
    }
}

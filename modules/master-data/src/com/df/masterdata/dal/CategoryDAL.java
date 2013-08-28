package com.df.masterdata.dal;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.Constants.CATEGORY;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.exception.CategoryException;

public class CategoryDAL extends MasterDataAccessFoundation {

    @Inject
    private ItemTemplateDAL itemTemplateDAL;

    public void setItemTemplateDAL(ItemTemplateDAL itemTemplateDAL) {
	this.itemTemplateDAL = itemTemplateDAL;
    }

    public List<Category> getRootCategories() {
	String entityName = this.getClassDescrptor(Category.class).getAlias();
	String eql = String.format("SELECT c FROM %s c WHERE c.parent IS NULL", entityName);
	TypedQuery<Category> query = this.getEntityManager().createQuery(eql, Category.class);
	return query.getResultList();
    }

    public Category findCategoryByName(String name) {
	return findSingleEntityByProperty(Category.class, CATEGORY.NAME_PROPERTY, name);
    }

    public void removeCategoryById(long categoryId) {
	List<ItemTemplate> itemTemplates = itemTemplateDAL.getItemTemplateByCategory(categoryId);
	if (itemTemplates.size() > 0) {
	    throw CategoryException.itemTemplateListNotEmpty(categoryId);
	} else {
	    Category found = this.find(Category.class, categoryId);
	    if (found == null) {
		return;
	    } else if (found.getChildren().size() > 0) {
		throw CategoryException.descendantsExist(categoryId);
	    } else {
		super.removeMasterDataById(found, categoryId);
	    }
	}
    }

    public void newCategory(Category c, Long parentCategoryId) {
	if (parentCategoryId != null) {
	    Category parent = find(Category.class, parentCategoryId);
	    if (parent != null) {
		c.setParent(parent);
	    } else {
		throw CategoryException.parentCategoryNotFound(parentCategoryId);
	    }
	}
	if (this.findCategoryByName(c.getName()) != null) {
	    throw CategoryException.categoryWithNameExist(c.getName());
	}
	insert(c);
    }

}

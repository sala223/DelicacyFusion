package com.df.masterdata.dal;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.df.masterdata.entity.Category;
import com.df.masterdata.entity.Constants.CATEGORY;

public class CategoryDAL extends MasterDataAccessFoundation {

    public List<Category> getRootCategories() {
	String eql = String.format("SELECT FROM %s AS c WHERE c.parent IS NULL", CATEGORY.ENTITY_NAME);
	TypedQuery<Category> query = this.getEntityManager().createQuery(eql, Category.class);
	return query.getResultList();
    }

    public Category findCategoryByName(String name) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Category> query = builder.createQuery(Category.class);
	Root<Category> root = query.from(Category.class);
	query.where(builder.equal(root.get(CATEGORY.NAME_PROPERTY), name));
	return executeSingleQuery(query);
    }

    public int removeCategoryById(long categoryId) {
	return super.removeMasterDataById(Category.class, categoryId);
    }
}

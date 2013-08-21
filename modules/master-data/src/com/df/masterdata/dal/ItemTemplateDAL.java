package com.df.masterdata.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.df.masterdata.entity.Constants.CATEGORY;
import com.df.masterdata.entity.Constants.ITEM_TEMPLATE;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.exception.ItemTemplateException;

public class ItemTemplateDAL extends MasterDataAccessFoundation {

    public void newItem(ItemTemplate itl) {
	String code = itl.getCode();
	ItemTemplate found = this.findSingleEntityByProperty(ItemTemplate.class, ITEM_TEMPLATE.CODE_PROPERTY, code);
	if (found != null) {
	    throw ItemTemplateException.itemTemplateWithCodeExist(code);
	} else {
	    found = this.findSingleEntityByProperty(ItemTemplate.class, ITEM_TEMPLATE.NAME_PROPERTY, itl.getName());
	    if (found != null) {
		throw ItemTemplateException.itemTemplateWithNameExist(itl.getName());
	    }
	}
	this.insert(itl);
    }

    public void disableItemTemplate(long itemTemplateId) {
	this.disableMasterData(ItemTemplate.class, itemTemplateId);
    }

    public List<ItemTemplate> getItemTemplateByCategory(long categoryId) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<ItemTemplate> query = builder.createQuery(ItemTemplate.class);
	Root<ItemTemplate> root = query.from(ItemTemplate.class);
	Join<Object, Object> categoriesJoin = root.join(ITEM_TEMPLATE.CATEGORIES_PROPERTY);
	Predicate categoryEqual = builder.equal(categoriesJoin.get(CATEGORY.ID_PROPERTY), categoryId);
	query.where(categoryEqual);
	EntityManager em = getEntityManager();
	return em.createQuery(query).getResultList();
    }
}

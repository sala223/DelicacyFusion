package com.df.masterdata.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.Assert;

import com.df.masterdata.entity.Constants.ITEM_TEMPLATE;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.exception.ItemTemplateException;

public class ItemTemplateDao extends MasterDataAccessFoundation {

	public void newItemTemplate(ItemTemplate itl) {
		Assert.notNull(itl.getCode());
		Assert.notNull(itl.getName());
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

	public void disableItemTemplate(String itemCode) {
		this.disableMasterData(ItemTemplate.class, itemCode);
	}

	public void enableItemTemplate(String itemCode) {
		this.enableMasterData(ItemTemplate.class, itemCode);
	}

	public ItemTemplate getItemTemplateByCode(String code) {
		return this.findSingleEntityByProperty(ItemTemplate.class, ITEM_TEMPLATE.CODE_PROPERTY, code);
	}

	public ItemTemplate getItemTemplateByName(String name) {
		return this.findSingleEntityByProperty(ItemTemplate.class, ITEM_TEMPLATE.NAME_PROPERTY, name);
	}

	public List<ItemTemplate> getItemTemplateByCategory(String categoryCode, int firstResult, int maxResult,
	        boolean includeDisabled) {
		CriteriaBuilder builder = createQueryBuilder();
		CriteriaQuery<ItemTemplate> query = builder.createQuery(ItemTemplate.class);
		Root<ItemTemplate> root = query.from(ItemTemplate.class);
		Join<Object, Object> categoriesJoin = root.join(ITEM_TEMPLATE.CATEGORIES_PROPERTY);
		List<Predicate> predicates = new ArrayList<Predicate>();
		Predicate categoryEqual = builder.equal(categoriesJoin.get("code"), categoryCode);
		predicates.add(categoryEqual);
		if (!includeDisabled) {
			predicates.add(builder.equal(root.get(ITEM_TEMPLATE.IS_ENABLED_PROPERTY), true));
		}
		query.where(categoryEqual);
		EntityManager em = getEntityManager();
		TypedQuery<ItemTemplate> typedQuery = em.createQuery(query);
		typedQuery.setFirstResult(firstResult);
		typedQuery.setMaxResults(maxResult);
		return typedQuery.getResultList();
	}

	public List<ItemTemplate> getItemTemplateByCategory(String categoryCode) {
		return this.getItemTemplateByCategory(categoryCode, 0, Integer.MAX_VALUE, false);
	}
}

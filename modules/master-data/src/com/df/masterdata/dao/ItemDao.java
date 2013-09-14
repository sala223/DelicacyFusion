package com.df.masterdata.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.springframework.util.Assert;

import com.df.masterdata.entity.Constants.ITEM;
import com.df.masterdata.entity.Constants.ITEM_TEMPLATE;
import com.df.masterdata.entity.Constants.MASTERDATA;
import com.df.masterdata.entity.Constants;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.ItemType;
import com.df.masterdata.exception.ItemException;

public class ItemDao extends StoreAwareMasterDataAccessFoundation {

    public void newItem(Item item) {
	ItemTemplate template = item.getItemTemplate();
	Assert.notNull(template);
	Assert.notNull(item.getStoreCode());
	Item found = this.getItemByItemCode(item.getStoreCode(), item.getItemTemplate().getCode());
	if (found == null) {
	    if (!item.getItemTemplate().isEnabled()) {
		throw ItemException.itemTemplateDisabled(item.getItemTemplate().getCode());
	    }
	    this.insert(item);
	} else {
	    throw ItemException.itemWithCodeAlreadyExist(item.getItemTemplate().getCode());
	}
    }

    public Item getItemByItemCode(String storeCode, String itemCode) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	Join<Object, Object> templateJoin = root.join(ITEM.TEMPLATE_PROPERTY);
	List<Predicate> predicates = new ArrayList<Predicate>();
	predicates.add(builder.equal(templateJoin.get(ITEM_TEMPLATE.CODE_PROPERTY), itemCode));
	predicates.add(builder.equal(root.get(ITEM.STORE_CODE_PROPERTY), storeCode));
	query.where(predicates.toArray(new Predicate[0]));
	EntityManager em = getEntityManager();
	TypedQuery<Item> typedQuery = em.createQuery(query);
	List<Item> results = typedQuery.getResultList();
	if (results.size() >= 1) {
	    return results.get(0);
	} else {
	    return null;
	}
    }

    public Map<String, Long> getItemCountGroupByCategory(String storeCode) {
	String eqlFmt = "SELECT c, COUNT(i) FROM Item i INNER JOIN i.itemTemplate t LEFT OUTER JOIN t.categories c";
	eqlFmt += " WHERE i.%s=:STORE_CODE AND t." + MASTERDATA.IS_ENABLED_PROPERTY + " =:IS_ENABLED";
	eqlFmt += " GROUP BY c";
	String eql = String.format(eqlFmt, ITEM.STORE_CODE_PROPERTY);
	Query query = this.getEntityManager().createQuery(eql);
	query.setParameter("STORE_CODE", storeCode);
	query.setParameter("IS_ENABLED", true);
	List<?> rows = query.getResultList();
	Map<String, Long> map = new HashMap<String, Long>();
	for (Object row : rows) {
	    if (row.getClass().isArray()) {
		Object[] rowArray = (Object[]) row;
		map.put((String) rowArray[0], (Long) rowArray[1]);
	    }
	}
	return map;
    }

    public List<Item> getItemsByCategory(String categoryCode, String storeCode) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	Join<Object, Object> templateJoin = root.join(ITEM.TEMPLATE_PROPERTY);
	Predicate categoryEqual = builder.equal(templateJoin.get(ITEM_TEMPLATE.CATEGORIES_PROPERTY), categoryCode);
	Predicate ownerIdEqual = builder.equal(root.get(getStoreCodePropertyName()), storeCode);
	query.where(builder.and(categoryEqual, ownerIdEqual));
	EntityManager em = getEntityManager();
	return em.createQuery(query).getResultList();
    }

    public List<Item> getFoodsByCategory(String categoryCode, String storeCode) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	Join<Object, Object> templateJoin = root.join(ITEM.TEMPLATE_PROPERTY);
	Predicate typeEqual = builder.equal(templateJoin.get(ITEM_TEMPLATE.TYPE_PROPERTY), ItemType.Food);
	Predicate categoryEqual = builder.equal(templateJoin.get(ITEM_TEMPLATE.CATEGORIES_PROPERTY), categoryCode);
	Predicate ownerIdEqual = builder.equal(root.get(getStoreCodePropertyName()), storeCode);
	query.where(builder.and(builder.and(categoryEqual, ownerIdEqual), typeEqual));
	EntityManager em = getEntityManager();
	return em.createQuery(query).getResultList();
    }

    public long allFoodCount(String storeCode, boolean includeDisabled) {
	EntityManager em = this.getEntityManager();
	ClassDescriptor cd = this.getClassDescrptor(Item.class);
	String eql = "SELECT COUNT(o) FROM %s o INNER JOIN o.itemTemplate t ";
	eql += "WHERE o.%s=:STORE_CODE and t." + Constants.ITEM_TEMPLATE.TYPE_PROPERTY + "=:TYPE ";
	if (!includeDisabled) {
	    eql += " AND o." + Constants.MASTERDATA.IS_ENABLED_PROPERTY + "=:IS_ENABLED";
	}
	Query query = em.createQuery(String.format(eql, cd.getAlias(), getStoreCodePropertyName()));
	if (!includeDisabled) {
	    query.setParameter("IS_ENABLED", true);
	}
	query.setParameter("STORE_CODE", storeCode);
	query.setParameter("TYPE", ItemType.Food);
	return (Long) query.getSingleResult();
    }

    public List<Item> allFood(String storeCode, int firstResult, int maxResult, boolean includeDisabled) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	List<Predicate> predicates = new ArrayList<Predicate>();
	if (!includeDisabled) {
	    predicates.add(builder.equal(root.get(Constants.MASTERDATA.IS_ENABLED_PROPERTY), true));
	}
	Predicate storeIdEqual = builder.equal(root.get(getStoreCodePropertyName()), storeCode);
	predicates.add(builder.and(storeIdEqual));
	query.where(predicates.toArray(new Predicate[0]));
	TypedQuery<Item> typeQuery = this.getEntityManager().createQuery(query);
	typeQuery.setFirstResult(firstResult);
	typeQuery.setMaxResults(maxResult);
	return typeQuery.getResultList();
    }

}

package com.df.masterdata.dal;

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

import org.springframework.util.Assert;

import com.df.masterdata.entity.Constants.CATEGORY;
import com.df.masterdata.entity.Constants.ITEM;
import com.df.masterdata.entity.Constants.ITEM_TEMPLATE;
import com.df.masterdata.entity.Constants.MASTERDATA;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.exception.ItemException;

public class ItemDAL extends StoreAwareMasterDataAccessFoundation {

    public void newItem(Item item) {
	ItemTemplate template = item.getItemTemplate();
	Assert.notNull(template);
	Assert.notNull(item.getStoreId());
	Item found = this.getItemByItemCode(item.getStoreId(), item.getItemTemplate().getCode());
	if (found == null) {
	    if (!item.getItemTemplate().isEnabled()) {
		throw ItemException.itemTemplateDisabled(item.getItemTemplate().getCode());
	    }
	    this.insert(item);
	} else {
	    throw ItemException.itemWithCodeAlreadyExist(item.getItemTemplate().getCode());
	}
    }

    public Item getItemByItemCode(long storeId, String itemCode) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	Join<Object, Object> templateJoin = root.join(ITEM.TEMPLATE_PROPERTY);
	List<Predicate> predicates = new ArrayList<Predicate>();
	predicates.add(builder.equal(templateJoin.get(ITEM_TEMPLATE.CODE_PROPERTY), itemCode));
	predicates.add(builder.equal(root.get(ITEM.STORE_ID_PROPERTY), storeId));
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

    public Map<Long, Long> getItemCountGroupByCategory(long storeId) {
	String eql = "SELECT c.id, COUNT(i) FROM Item i INNER JOIN i.itemTemplate t INNER JOIN t.categories c";
	eql += " WHERE i.storeId=:STORE_ID AND t." + MASTERDATA.IS_ENABLED_PROPERTY + " =:IS_ENABLED";
	eql += " GROUP BY c.id ";
	Query query = this.getEntityManager().createQuery(eql);
	query.setParameter("STORE_ID", storeId);
	query.setParameter("IS_ENABLED", true);
	List<?> rows = query.getResultList();
	Map<Long, Long> map = new HashMap<Long, Long>();
	for (Object row : rows) {
	    if (row.getClass().isArray()) {
		Object[] rowArray = (Object[]) row;
		map.put((Long) rowArray[0], (Long) rowArray[1]);
	    }
	}
	return map;
    }

    public List<Item> getItemsByCategory(long categoryId, long storeId) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	Join<Object, Object> templateJoin = root.join(ITEM.TEMPLATE_PROPERTY);
	Join<Object, Object> categoriesJoin = templateJoin.join(ITEM_TEMPLATE.CATEGORIES_PROPERTY);
	Predicate categoryEqual = builder.equal(categoriesJoin.get(CATEGORY.ID_PROPERTY), categoryId);
	Predicate ownerIdEqual = builder.equal(root.get(getStoreIdPropertyName()), storeId);
	query.where(builder.and(categoryEqual, ownerIdEqual));
	EntityManager em = getEntityManager();
	return em.createQuery(query).getResultList();
    }

}

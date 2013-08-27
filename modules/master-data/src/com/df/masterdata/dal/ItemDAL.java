package com.df.masterdata.dal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.df.masterdata.entity.Constants.ITEM;
import com.df.masterdata.entity.Constants.MASTERDATA;
import com.df.masterdata.entity.Item;

public class ItemDAL extends StoreAwareMasterDataAccessFoundation {

    public void newItem(Item item) {
	this.insert(item);
    }

    public Map<Long, Long> getItemCountGroupByCategory(long storeId) {
	String eql = "SELECT i.itemTemplate.categories.id, COUNT(i) FROM Item i group by i.Categories.id ";
	eql += " having i.storeId=:STORE_ID and i.itemTemplate." + MASTERDATA.IS_ENABLED_PROPERTY + " =:IS_ENABLED";
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

    public List<Item> getItemsByCategory(long categoryId, Long storeId) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	Predicate categoryEqual = builder.equal(root.get(ITEM.CATEGORY_ID_PROPERTY), categoryId);
	Predicate ownerIsNull = builder.isNull(root.get(getStoreIdPropertyName()));
	if (storeId != null) {
	    Predicate ownerIdEqual = builder.equal(root.get(getStoreIdPropertyName()), storeId);
	    query.where(builder.and(categoryEqual, builder.or(ownerIdEqual, ownerIsNull)));
	} else {
	    query.where(builder.and(categoryEqual, ownerIsNull));
	}
	EntityManager em = getEntityManager();
	return em.createQuery(query).getResultList();
    }

}

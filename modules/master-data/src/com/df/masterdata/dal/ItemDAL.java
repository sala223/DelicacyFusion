package com.df.masterdata.dal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.df.masterdata.entity.Constants.ITEM;
import com.df.masterdata.entity.Item;

public class ItemDAL extends MasterDataAccessFoundation {

    public void newItem(Item item) {
	this.insert(item);
    }

    public Map<Long, Long> getItemCountGroupByCategory() {
	String eql = "SELECT i.Categories.id, COUNT(i) FROM Item i group by i.Categories.id";
	Query query = this.getEntityManager().createQuery(eql);
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

    public List<Item> getItemsByCategory(long categoryId) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Item> query = builder.createQuery(Item.class);
	Root<Item> root = query.from(Item.class);
	query.where(builder.equal(root.get(ITEM.CATEGORY_ID_PROPERTY), categoryId));
	EntityManager em = getEntityManager();
	return em.createQuery(query).getResultList();
    }

}

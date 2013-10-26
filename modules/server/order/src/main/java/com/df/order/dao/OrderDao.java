package com.df.order.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.dao.ItemDao;
import com.df.order.entity.Constants;
import com.df.order.entity.Order;

public class OrderDao extends TransactionDataAccessFoundation {

    @Autowired
    private ItemDao itemDao;

    public void setItemDao(ItemDao itemDao) {
	this.itemDao = itemDao;
    }

    public void updateOrderStatus() {

    }

    public List<Order> getLatestOrders(long userId, int number) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Order> query = builder.createQuery(Order.class);
	Root<Order> root = query.from(Order.class);
	query.where(builder.equal(root.get(Constants.ORDER.OWNER_ID), userId));
	EntityManager em = getEntityManager();
	TypedQuery<Order> tQuery = em.createQuery(query);
	tQuery.setMaxResults(number);
	return tQuery.getResultList();
    }
}

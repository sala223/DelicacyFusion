package com.df.order.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.core.persist.eclipselink.Property;
import com.df.order.entity.Order;
import com.df.order.entity.ServiceCard;
import com.df.order.entity.Constants.SERVICE_CARD;
import com.df.order.exception.ServiceCardException;

public class ServiceCardDao extends EclipseLinkDataAccessFoundation {

	public ServiceCard createServiceCard(String storeCode, List<String> tableCodes) {
		ServiceCard card = new ServiceCard();
		card.setStoreCode(storeCode);
		card.setTables(tableCodes);
		this.getEntityManager().persist(card);
		return card;
	}

	public ServiceCard getServiceCardByTable(String storeCode, String tableCode) {
		CriteriaBuilder builder = createQueryBuilder();
		CriteriaQuery<ServiceCard> query = builder.createQuery(ServiceCard.class);
		Root<ServiceCard> root = query.from(ServiceCard.class);
		Predicate storeCodeEqal = builder.equal(root.get(SERVICE_CARD.STORE_CODE), storeCode);
		Path<List<String>> tablesPath = root.get(SERVICE_CARD.TABLES);
		Predicate tableIsMember = builder.isMember(tableCode, tablesPath);
		query.where(storeCodeEqal, tableIsMember);
		return this.executeSingleQuery(query);
	}

	public ServiceCard getServiceCardById(String storeCode, long serviceCardId) {
		return this.findSingleEntityByProperty(ServiceCard.class, SERVICE_CARD.ID, serviceCardId);
	}

	public ServiceCard getServiceCardByOrderId(String storeCode, long orderId) {
		Property<String> p1 = new Property<String>(SERVICE_CARD.STORE_CODE, storeCode);
		Property<Long> p2 = new Property<Long>(SERVICE_CARD.ORDER_ID, orderId);
		return this.findSingleEntityByProperties(ServiceCard.class, p1, p2);
	}

	public void updateServiceCardTables(String storeCode, long serviceCardId, List<String> tableCodes) {
		ServiceCard card = this.findSingleEntityByProperty(ServiceCard.class, SERVICE_CARD.ID, serviceCardId);
		if (card == null) {
			int errorCode = ServiceCardException.INVALID_SERVICE_CARD_ID;
			throw new ServiceCardException(errorCode, "Invalid service card id " + serviceCardId);
		}
		card.setTables(tableCodes);
		this.update(card);
	}

	public void deleteServiceCard(String storeCode, long serviceCardId) {
		Property<Long> p1 = new Property<Long>(SERVICE_CARD.ID, serviceCardId);
		Property<String> p2 = new Property<String>(SERVICE_CARD.STORE_CODE, storeCode);
		this.deleteEntityByProperties(ServiceCard.class, p1, p2);
	}

	public void associateServiceCardWithOrder(String storeCode, long serviceCardId, Order order) {
		ServiceCard card = this.findSingleEntityByProperty(ServiceCard.class, SERVICE_CARD.ID, serviceCardId);
		if (card == null) {
			int errorCode = ServiceCardException.INVALID_SERVICE_CARD_ID;
			throw new ServiceCardException(errorCode, "Invalid service card id " + serviceCardId);
		}
		card.setOrderId(order.getTransactionId());
		this.update(card);
	}

	public List<ServiceCard> all() {
		return super.all(ServiceCard.class);
	}

}

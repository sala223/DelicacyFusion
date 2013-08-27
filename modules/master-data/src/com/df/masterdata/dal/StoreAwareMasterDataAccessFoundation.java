package com.df.masterdata.dal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.descriptors.ClassDescriptor;

import com.df.masterdata.entity.Constants;
import com.df.masterdata.entity.MasterData;

public class StoreAwareMasterDataAccessFoundation extends MasterDataAccessFoundation {

    public <T extends MasterData> List<T> all(Long storeId, Class<T> entityType) {
	return all(storeId, entityType, 0, Integer.MAX_VALUE, false);
    }

    public <T extends MasterData> int allCount(Long storeId, Class<T> entityType, boolean includeDisabled) {
	EntityManager em = this.getEntityManager();
	ClassDescriptor cd = this.getClassDescrptor(entityType);
	String eql = "SELECT COUNT(O) FROM %s o ";
	if (storeId != null) {
	    eql = "WHERE o.%s=:STORE_ID ";
	}
	if (!includeDisabled) {
	    if (storeId != null) {
		eql += " AND o." + Constants.MASTERDATA.IS_ENABLED_PROPERTY + "=:IS_ENABLED";
	    } else {
		eql += " WHERE o." + Constants.MASTERDATA.IS_ENABLED_PROPERTY + "=:IS_ENABLED";

	    }
	}
	Query query = em.createQuery(String.format(eql, cd.getAlias(), getStoreIdPropertyName()));
	if (!includeDisabled) {
	    query.setParameter("IS_ENABLED", true);
	}
	if (storeId != null) {
	    query.setParameter("STORE_ID", storeId);
	}
	return (Integer) query.getSingleResult();
    }

    public <T extends MasterData> List<T> all(Long storeId, Class<T> entityType, int firstResult, int maxResult,
	    boolean includeDisabled) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<T> query = builder.createQuery(entityType);
	Root<T> root = query.from(entityType);
	List<Predicate> predicates = new ArrayList<Predicate>();
	if (!includeDisabled) {
	    predicates.add(builder.equal(root.get(Constants.MASTERDATA.IS_ENABLED_PROPERTY), true));
	}
	Predicate storeIsNull = builder.isNull(root.get(getStoreIdPropertyName()));
	if (storeId != null) {
	    Predicate storeIdEqual = builder.equal(root.get(getStoreIdPropertyName()), storeId);
	    predicates.add(builder.and(builder.or(storeIdEqual, storeIsNull)));
	} else {
	    predicates.add(storeIsNull);
	}
	query.where(predicates.toArray(new Predicate[0]));
	TypedQuery<T> typeQuery = this.getEntityManager().createQuery(query);
	typeQuery.setFirstResult(firstResult);
	typeQuery.setMaxResults(maxResult);
	return typeQuery.getResultList();
    }

    protected String getStoreIdPropertyName() {
	return "storeId";
    }
}

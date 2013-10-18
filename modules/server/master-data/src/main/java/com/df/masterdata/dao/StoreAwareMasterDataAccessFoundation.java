package com.df.masterdata.dao;

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

import com.df.core.persist.eclipselink.Property;
import com.df.masterdata.entity.Constants;
import com.df.masterdata.entity.Constants.STORE_AWARE_MASTERDATA;
import com.df.masterdata.entity.StoreAwareMasterData;

public class StoreAwareMasterDataAccessFoundation extends MasterDataAccessFoundation {

    public <T extends StoreAwareMasterData> List<T> all(String storeCode, Class<T> storeMasterDataType) {
	return all(storeCode, storeMasterDataType, 0, Integer.MAX_VALUE, false);
    }

    public <T extends StoreAwareMasterData> int allCount(String storeCode, Class<T> storeMasterDataType,
	    boolean includeDisabled) {
	EntityManager em = this.getEntityManager();
	ClassDescriptor cd = this.getClassDescrptor(storeMasterDataType);
	String eql = "SELECT COUNT(O) FROM %s o WHERE o.%s=:STORE_CODE ";
	if (!includeDisabled) {
	    eql += "AND o." + Constants.MASTERDATA.IS_ENABLED_PROPERTY + "=:IS_ENABLED";
	}
	Query query = em.createQuery(String.format(eql, cd.getAlias(), getStoreCodePropertyName()));
	if (!includeDisabled) {
	    query.setParameter("IS_ENABLED", true);
	}
	query.setParameter("STORE_CODE", storeCode);
	return (Integer) query.getSingleResult();
    }

    public <T extends StoreAwareMasterData> List<T> all(String storeCode, Class<T> storeMasterDataType,
	    int firstResult, int maxResult, boolean includeDisabled) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<T> query = builder.createQuery(storeMasterDataType);
	Root<T> root = query.from(storeMasterDataType);
	List<Predicate> predicates = new ArrayList<Predicate>();
	if (!includeDisabled) {
	    predicates.add(builder.equal(root.get(Constants.MASTERDATA.IS_ENABLED_PROPERTY), true));
	}
	Predicate storeCodeEqual = builder.equal(root.get(getStoreCodePropertyName()), storeCode);
	predicates.add(storeCodeEqual);
	query.where(predicates.toArray(new Predicate[0]));
	TypedQuery<T> typeQuery = this.getEntityManager().createQuery(query);
	typeQuery.setFirstResult(firstResult);
	typeQuery.setMaxResults(maxResult);
	return typeQuery.getResultList();
    }

    protected String getStoreCodePropertyName() {
	return STORE_AWARE_MASTERDATA.STORE_CODE_PROPERTY;
    }

    protected <T extends StoreAwareMasterData> T findSingleEntityByProperty(Class<T> storeMasterDataType,
	    String storeCode, String propertyName, Object propertyValue) {
	Property<String> p1 = new Property<String>(getStoreCodePropertyName(), storeCode);
	Property<Object> p2 = new Property<Object>(propertyName, propertyValue);
	return this.findSingleEntityByProperties(storeMasterDataType, p1, p2);
    }

}

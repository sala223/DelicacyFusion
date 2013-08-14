package com.df.masterdata.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.descriptors.ClassDescriptor;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.core.persist.exception.DataAccessException;
import com.df.masterdata.entity.Constants;

public class MasterDataAccessFoundation extends EclipseLinkDataAccessFoundation {

    public int removeMasterDataById(Class<?> masterDataType, long id) {
	String eql = "DELETE FROM %s AS c WHERE c.id=:ID";
	ClassDescriptor cd = this.getClassDescrptor(masterDataType);
	if (cd != null) {
	    Query query = this.getEntityManager().createQuery(String.format(eql, cd.getAlias()));
	    query.setParameter("ID", id);
	    return query.executeUpdate();
	} else {
	    String msg = "%s is not a entity type.";
	    throw new DataAccessException(msg, masterDataType.getName());
	}
    }

    @Override
    public <T> List<T> all(Class<T> entityType) {
	return all(entityType, 0, Integer.MAX_VALUE, false);
    }

    public <T> int allCount(Class<T> entityType, boolean includeDisabled) {
	EntityManager em = this.getEntityManager();
	ClassDescriptor cd = this.getClassDescrptor(entityType);
	String eql = "SELECT COUNT(O) FROM %s o ";
	if (!includeDisabled) {
	    eql += " WHERE o." + Constants.MASTERDATA.IS_ENABLED_PROPERTY + "=:IS_ENABLED";
	}
	Query query = em.createQuery(String.format(eql, cd.getAlias()));
	if (!includeDisabled) {
	    query.setParameter("IS_ENABLED", true);
	}
	return (Integer) query.getSingleResult();
    }

    public <T> List<T> all(Class<T> entityType, int firstResult, int maxResult, boolean includeDisabled) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<T> query = builder.createQuery(entityType);
	Root<T> root = query.from(entityType);
	if (!includeDisabled) {
	    query.where(builder.equal(root.get(Constants.MASTERDATA.IS_ENABLED_PROPERTY), true));
	}
	TypedQuery<T> typeQuery = this.getEntityManager().createQuery(query);
	typeQuery.setFirstResult(firstResult);
	typeQuery.setMaxResults(maxResult);
	return typeQuery.getResultList();
    }
}

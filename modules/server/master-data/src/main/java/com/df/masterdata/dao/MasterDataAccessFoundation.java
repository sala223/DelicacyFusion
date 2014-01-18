package com.df.masterdata.dao;

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
import com.df.masterdata.entity.MasterData;

public class MasterDataAccessFoundation extends EclipseLinkDataAccessFoundation {

	public <T extends MasterData> int removeMasterDataByCode(Class<T> masterDataType, String code) {
		String eqlFormat = "DELETE FROM %s t WHERE t.%s=:CODE";
		String entityName = this.getClassDescrptor(masterDataType).getAlias();
		String eql = String.format(eqlFormat, entityName, Constants.MASTERDATA.CODE_PROPERTY);
		Query query = this.getEntityManager().createQuery(eql);
		query.setParameter("CODE", code);
		return query.executeUpdate();
	}

	public <T extends MasterData> int enableMasterData(Class<T> masterDataType, String code) {
		String eqlFormat = "UPDATE FROM %s t SET t.%s=:ISENABLED WHERE t.%s=:CODE";
		ClassDescriptor cd = this.getClassDescrptor(masterDataType);
		if (cd != null) {
			String isEnabledProperty = Constants.MASTERDATA.IS_ENABLED_PROPERTY;
			String eql = String.format(eqlFormat, cd.getAlias(), isEnabledProperty, Constants.MASTERDATA.CODE_PROPERTY);
			Query query = this.getEntityManager().createQuery(String.format(eql, cd.getAlias()));
			query.setParameter("CODE", code);
			query.setParameter("ISENABLED", true);
			return query.executeUpdate();
		} else {
			String msg = "%s is not a entity type.";
			throw new DataAccessException(msg, masterDataType.getName());
		}
	}

	public <T extends MasterData> int disableMasterData(Class<T> masterDataType, String code) {
		String eqlFormat = "UPDATE FROM %s t SET t.%s=:ISENABLED WHERE t.%s=:CODE";
		ClassDescriptor cd = this.getClassDescrptor(masterDataType);
		if (cd != null) {
			String isEnabledProperty = Constants.MASTERDATA.IS_ENABLED_PROPERTY;
			String eql = String.format(eqlFormat, cd.getAlias(), isEnabledProperty, Constants.MASTERDATA.CODE_PROPERTY);
			Query query = this.getEntityManager().createQuery(String.format(eql, cd.getAlias()));
			query.setParameter("CODE", code);
			query.setParameter("ISENABLED", false);
			return query.executeUpdate();
		} else {
			String msg = "%s is not a entity type.";
			throw new DataAccessException(msg, masterDataType.getName());
		}
	}

	public <T extends MasterData> long allCount(Class<T> entityType, boolean includeDisabled) {
		EntityManager em = this.getEntityManager();
		ClassDescriptor cd = this.getClassDescrptor(entityType);
		String eql = "SELECT COUNT(O) FROM %s o";
		if (!includeDisabled) {
			eql += " WHERE o." + Constants.MASTERDATA.IS_ENABLED_PROPERTY + "=:IS_ENABLED";
		}
		Query query = em.createQuery(String.format(eql, cd.getAlias()));
		if (!includeDisabled) {
			query.setParameter("IS_ENABLED", true);
		}
		return (Long) query.getSingleResult();
	}

	public <T extends MasterData> List<T> all(Class<T> entityType, int firstResult, int maxResult,
	        boolean includeDisabled) {
		return all(entityType, firstResult, maxResult, includeDisabled, false);
	}

	public <T extends MasterData> List<T> all(Class<T> entityType, int firstResult, int maxResult,
	        boolean includeDisabled, boolean refreshCache) {
		CriteriaBuilder builder = createQueryBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityType);
		Root<T> root = query.from(entityType);
		if (!includeDisabled) {
			query.where(builder.equal(root.get(Constants.MASTERDATA.IS_ENABLED_PROPERTY), true));
		}
		TypedQuery<T> typeQuery = this.getEntityManager().createQuery(query);
		typeQuery.setFirstResult(firstResult);
		typeQuery.setMaxResults(maxResult);
		if (refreshCache) {
			typeQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
		}
		return typeQuery.getResultList();
	}
}

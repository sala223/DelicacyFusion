package com.df.crm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.springframework.util.Assert;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.crm.entity.Constants;
import com.df.crm.entity.Tenant;
import com.df.crm.exception.TenantException;

public class TenantDao extends EclipseLinkDataAccessFoundation {

	public Tenant newTenant(Tenant tenant) {
		String code = tenant.getCode();
		String name = tenant.getName();
		Assert.notNull(code);
		Assert.notNull(name);
		Tenant found = this.findSingleEntityByProperty(Tenant.class, Constants.TENANT.CODE_PROPERTY, code);
		if (found != null) {
			throw TenantException.tenantWithCodeAlreadyExist(code);
		}
		found = this.findSingleEntityByProperty(Tenant.class, Constants.TENANT.NAME_PROPERTY, name);
		if (found != null) {
			throw TenantException.tenantWithNameAlreadyExist(name);
		}
		this.insert(tenant);
		return tenant;
	}

	public Tenant findTenantByCode(String tenantCode) {
		return this.find(Tenant.class, tenantCode);
	}

	public Tenant findTenantByName(String name) {
		return this.findSingleEntityByProperty(Tenant.class, Constants.TENANT.NAME_PROPERTY, name);
	}

	public boolean disableTenant(String tenantCode) {
		Tenant tenant = this.find(Tenant.class, tenantCode);
		if (tenant == null) {
			return false;
		} else {
			tenant.setEnabled(false);
			this.update(tenant);
			return true;
		}
	}

	public long allTenantCount(Tenant tenant) {
		EntityManager em = this.getEntityManager();
		ClassDescriptor cd = this.getClassDescrptor(Tenant.class);
		String eql = "SELECT COUNT(O) FROM %s o";
		eql += " WHERE o." + Constants.TENANT.IS_ENABLED_PROPERTY + "=:IS_ENABLED";
		Query query = em.createQuery(String.format(eql, cd.getAlias()));
		query.setParameter("IS_ENABLED", true);
		return (Long) query.getSingleResult();
	}

	public List<Tenant> getTenants(int firstResult, int maxResult, boolean refreshCache) {
		CriteriaBuilder builder = createQueryBuilder();
		CriteriaQuery<Tenant> query = builder.createQuery(Tenant.class);
		Root<Tenant> root = query.from(Tenant.class);
		query.where(builder.equal(root.get(Constants.TENANT.IS_ENABLED_PROPERTY), true));
		TypedQuery<Tenant> typeQuery = this.getEntityManager().createQuery(query);
		typeQuery.setFirstResult(firstResult);
		typeQuery.setMaxResults(maxResult);
		if (refreshCache) {
			typeQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
		}
		return typeQuery.getResultList();
	}
}

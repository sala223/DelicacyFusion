package com.df.core.persist.eclipselink;

import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.queries.FetchGroup;
import org.eclipse.persistence.sessions.Session;

import com.df.core.persist.exception.DataAccessException;
import com.df.core.persist.jpa.JPADataAccessFoundation;

public class EclipseLinkDataAccessFoundation extends JPADataAccessFoundation {

    public <T> List<T> all(Class<T> entityType) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<T> query = builder.createQuery(entityType);
	query.from(entityType);
	TypedQuery<T> typeQuery = this.getEntityManager().createQuery(query);
	return typeQuery.getResultList();
    }

    public void bulkInsert(List<?> objects) {
	EntityManager em = getEntityManager();
	for (Object obj : objects) {
	    em.persist(obj);
	}
    }

    public Object findView(ViewId vid, Object id) {
	String viewName = vid.getViewName();
	EntityManager em = getEntityManager();
	ClassDescriptor cd = getClassDescrptor(vid.getEntityType());
	FetchGroup fg = cd.getFetchGroupManager().getFetchGroup(viewName);
	Vector<String> fields = cd.getPrimaryKeyFieldNames();
	if (fields.size() != 1) {
	    String ft1 = "FindView only support the entity which does not have composite primary key,";
	    String ft2 = "however, the primary key of entity %s is %s";
	    throw new DataAccessException(ft1 + ft2, cd.getAlias(), fields);
	}
	String eql = String.format("SELECT FROM %s o WHERE o.%s = :ID", cd.getPrimaryKeyFieldNames().get(0));
	Query query = em.createQuery(eql);
	query.setParameter("ID", id);
	query.setHint(QueryHints.FETCH_GROUP_NAME, fg);
	return query.getSingleResult();
    }

    public <T> List<T> queryView(ViewId vid, CriteriaQuery<T> query) {
	EntityManager em = getEntityManager();
	String viewName = vid.getViewName();
	ClassDescriptor cd = getClassDescrptor(vid.getEntityType());
	FetchGroup fg = cd.getFetchGroupManager().getFetchGroup(viewName);
	if (fg == null) {
	    throw new DataAccessException("View %s is not defined in entity %s", viewName, cd.getAlias());
	}
	TypedQuery<T> tq = em.createQuery(query);
	tq.setHint(QueryHints.FETCH_GROUP_NAME, fg);
	return tq.getResultList();
    }

    protected ClassDescriptor getClassDescrptor(String entity) {
	Session session = getSession();
	return session.getClassDescriptorForAlias(entity);
    }

    protected ClassDescriptor getClassDescrptor(Class<?> entityClass) {
	Session session = getSession();
	return session.getClassDescriptor(entityClass);
    }

    protected Session getSession() {
	EntityManager em = getEntityManager();
	return em.unwrap(Session.class);
    }
}

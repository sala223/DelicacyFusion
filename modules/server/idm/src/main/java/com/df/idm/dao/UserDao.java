package com.df.idm.dao;

import java.util.List;

import javax.persistence.Query;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.idm.entity.Constants;
import com.df.idm.entity.ExternalUserReference.Provider;
import com.df.idm.entity.User;

public class UserDao extends EclipseLinkDataAccessFoundation implements Constants {

	public User getUserById(long id) {
		return this.find(User.class, id);
	}

	public User getUserByEmail(String email) {
		return findSingleEntityByProperty(User.class, USER.EMAIL_PROPERTY, email);
	}

	public User getUserByTelephone(String telephone) {
		return findSingleEntityByProperty(User.class, USER.TELEPHONE_PROPERTY, telephone);
	}

	public User getUserByExternalId(String externalId, Provider provider) {
		String fmt = "SELECT u FROM %s u LEFT OUTER JOIN u.%s e WHERE e.%s=:E_UID AND e.%s=:PROVIDER";
		String sql = String.format(fmt, this.getEntityName(User.class), USER.EXTERNAL_USER_REFERENCE_PROPERTY,
		        USER.EXTERNAL_USER_ID_PROPERTY, USER.EXTERNAL_USER_PROVIDER_PROPERTY);
		Query query = this.getEntityManager().createQuery(sql);
		query.setParameter("E_UID", externalId);
		query.setParameter("PROVIDER", provider);
		List<?> results = query.getResultList();
		if (results.size() > 0) {
			return (User) results.get(0);
		}
		return null;
	}

	public int updateUserPassword(String email, String newEncodedPassword) {
		String fmt = "UPDATE %s u set u.%s=:PASSWORD WHERE u.%s=:EMAIL";
		String sql = String.format(fmt, this.getEntityName(User.class), USER.PASSWORD_PROPERTY, USER.EMAIL_PROPERTY);
		Query query = this.getEntityManager().createQuery(sql);
		query.setParameter("PASSWORD", newEncodedPassword);
		query.setParameter("EMAIL", email);
		return query.executeUpdate();
	}

	public void createUser(User user) {
		super.insert(user);
	}
}

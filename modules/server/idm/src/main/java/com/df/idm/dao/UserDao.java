package com.df.idm.dao;

import javax.persistence.Query;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.idm.entity.Constants;
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

	public User getUserByWeiboAccount(String weiboAccount) {
		return findSingleEntityByProperty(User.class, USER.WEIBO_ACCOUNT_PROPERTY, weiboAccount);
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

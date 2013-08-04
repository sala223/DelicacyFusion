package com.df.masterdata.dal;

import javax.persistence.Query;

import org.eclipse.persistence.descriptors.ClassDescriptor;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.core.persist.exception.DataAccessException;

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
}

package com.df.order.dao;

import java.util.List;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.order.entity.Constants.TABLE_RESOURCE;
import com.df.order.entity.TableResource;

public class TableResourceDao extends EclipseLinkDataAccessFoundation {

	public List<TableResource> getTables(String storeCode) {
		return this.findEntityListByProperty(TableResource.class, TABLE_RESOURCE.STORE_CODE, storeCode);
	}
}

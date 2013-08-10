package com.df.masterdata.dal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.df.masterdata.entity.Constants.STORE;
import com.df.masterdata.entity.Store;

public class StoreDAL extends MasterDataAccessFoundation {

    public Store getStoreByName(String storeName) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<Store> query = builder.createQuery(Store.class);
	Root<Store> root = query.from(Store.class);
	query.where(builder.equal(root.get(STORE.NAME_PROPERTY), storeName));
	return executeSingleQuery(query);
    }
}

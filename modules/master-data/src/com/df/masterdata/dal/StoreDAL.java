package com.df.masterdata.dal;

import com.df.masterdata.entity.Constants.STORE;
import com.df.masterdata.entity.Store;

public class StoreDAL extends MasterDataAccessFoundation {

    public Store getStoreByName(String storeName) {
	return findSingleEntityByProperty(Store.class, STORE.NAME_PROPERTY, storeName);

    }
}

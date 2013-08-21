package com.df.masterdata.dal;

import java.util.List;

import com.df.masterdata.entity.Constants.STORE;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.StoreException;

public class StoreDAL extends MasterDataAccessFoundation {

    public Store getStoreByName(String storeName) {
	return findSingleEntityByProperty(Store.class, STORE.NAME_PROPERTY, storeName);
    }

    public Store getStoreByCode(String code) {
	return findSingleEntityByProperty(Store.class, STORE.CODE_PROPERTY, code);
    }

    public List<Store> getStoreList(boolean includeDisabled) {
	return this.all(Store.class, 0, Integer.MAX_VALUE, includeDisabled);
    }

    public void newStore(Store store) {
	if (getStoreByCode(store.getCode()) != null) {
	    throw StoreException.storeWithCodeAlreadyExist(store.getCode());
	} else if (getStoreByName(store.getName()) != null) {
	    throw StoreException.storeWithNameAlreadyExist(store.getName());
	}
	this.insert(store);
    }
}

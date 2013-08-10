package com.df.masterdata.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.StoreDAL;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.StoreException;
import com.df.masterdata.service.inf.StoreServiceInf;

public class StoreServiceImpl implements StoreServiceInf {

    @Inject
    private StoreDAL storeDAL;

    @Override
    @Transactional
    public void newStore(Store store) {
	if (storeDAL.getStoreByName(store.getName()) != null) {
	    throw StoreException.storeWithNameAlreadyExist(store.getName());
	} else {
	    storeDAL.insert(store);
	}
    }

    @Override
    public void disableStore(long storeId) {
	Store store = storeDAL.find(Store.class, storeId);
	if (store == null) {
	    throw StoreException.storeWithIdNotExist(storeId);
	}
	store.setEnabled(false);
	storeDAL.update(store);

    }

    @Override
    public List<Store> getStoreList() {
	return storeDAL.all(Store.class);
    }

    @Override
    public Store getStoreById(long storeId) {
	return storeDAL.find(Store.class, storeId);
    }

}

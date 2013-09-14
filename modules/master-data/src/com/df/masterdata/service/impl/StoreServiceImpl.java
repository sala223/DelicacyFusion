package com.df.masterdata.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.StoreDao;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.StoreException;
import com.df.masterdata.service.contract.StoreServiceInf;

@Transactional(isolation = Isolation.DEFAULT)
public class StoreServiceImpl implements StoreServiceInf {

    @Inject
    private StoreDao storeDao;

    public void setStoreDao(StoreDao storeDao) {
	this.storeDao = storeDao;
    }

    @Override
    public void newStore(Store store) {
	if (storeDao.getStoreByName(store.getName()) != null) {
	    throw StoreException.storeWithNameAlreadyExist(store.getName());
	} else {
	    storeDao.insert(store);
	}
    }

    @Override
    public void disableStore(String storeCode) {
	Store store = storeDao.getStoreByCode(storeCode);
	if (store == null) {
	    throw StoreException.storeWithCodeNotExist(storeCode);
	}
	store.setEnabled(false);
	storeDao.update(store);
    }

    @Override
    public void enableStore(String storeCode) {
	Store store = storeDao.getStoreByCode(storeCode);
	if (store == null) {
	    throw StoreException.storeWithCodeNotExist(storeCode);
	}
	store.setEnabled(true);
	storeDao.update(store);
    }

    @Override
    public List<Store> getStoreList() {
	return storeDao.all(Store.class, 0, Integer.MAX_VALUE, false);
    }

    @Override
    public Store getStoreByCode(String storeCode) {
	return storeDao.getStoreByCode(storeCode);
    }
}

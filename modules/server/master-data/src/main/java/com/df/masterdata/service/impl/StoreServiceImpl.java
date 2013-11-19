package com.df.masterdata.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.StoreDao;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.StoreException;
import com.df.masterdata.service.contract.StoreService;

@Transactional(isolation = Isolation.DEFAULT)
public class StoreServiceImpl implements StoreService {

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
	public void updateStore(Store store) {
		Store found = this.checkStore(store.getCode(), true);
		found.setAddress(store.getAddress());
		found.setBusinessHourFrom(store.getBusinessHourFrom());
		found.setBusinessHourTo(store.getBusinessHourTo());
		found.setChangedTime(new Date());
		found.setDescription(store.getDescription());
		found.setEnabled(store.isEnabled());
		found.setName(store.getName());
		found.setTelephone1(store.getTelephone1());
		found.setTelephone2(store.getTelephone2());
		found.setTrafficInfo(store.getTrafficInfo());
		storeDao.update(found);
	}

	@Override
	public List<Store> getStoreList() {
		return storeDao.all(Store.class, 0, Integer.MAX_VALUE, false);
	}

	@Override
	public Store getStoreByCode(String storeCode) {
		return storeDao.getStoreByCode(storeCode);
	}

	@Override
	public Store checkStore(String storeCode, boolean throwException) {
		Store found = storeDao.getStoreByCode(storeCode);
		if (found == null && throwException) {
			throw StoreException.storeWithCodeNotExist(storeCode);
		}
		return found;
	}
}

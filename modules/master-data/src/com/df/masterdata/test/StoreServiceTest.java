package com.df.masterdata.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.df.core.common.entity.Address;
import com.df.core.persist.testsuit.JPATestBase;
import com.df.masterdata.entity.Store;
import com.df.masterdata.service.inf.StoreServiceInf;

public class StoreServiceTest extends JPATestBase {

    @Autowired
    private StoreServiceInf storeService;

    @Test
    public void testNewStore() {
	Store store = new Store();
	store.setName("DanceQueue");
	store.setTelephone1("0731-34343433");
	store.setBusinessHourFrom(8);
	store.setBusinessHourFrom(22);
	Address address = new Address();
	address.setCountry("China");
	address.setProvince("Hunan");
	address.setCity("Changsha");
	address.setCounty("HuRong District");
	address.setAddress("Mawang dui");
	storeService.newStore(store);
	List<Store> stores = storeService.getStoreList();
	boolean hasFound = false;
	for (Store s : stores) {
	    if (s.getName().equals(store.getName())) {
		hasFound = true;
	    }
	}
	TestCase.assertTrue(hasFound);
    }
}

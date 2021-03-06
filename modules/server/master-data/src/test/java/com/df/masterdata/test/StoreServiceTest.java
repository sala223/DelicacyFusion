package com.df.masterdata.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.entity.Store;
import com.df.masterdata.service.contract.StoreService;

public class StoreServiceTest extends MasterDataJPABaseTest {

	@Autowired
	private StoreService storeService;

	@Test
	public void newStoreTest() {
		Store store = new Store();
		store.setName("DanceQueue");
		store.setCode("OMG");
		store.setTelephone1("0731-34343433");
		store.setBusinessHourFrom(8);
		store.setBusinessHourFrom(22);
		store.setAddress("Mawang dui");
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

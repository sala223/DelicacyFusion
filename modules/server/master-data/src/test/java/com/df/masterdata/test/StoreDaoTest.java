package com.df.masterdata.test;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.StoreDao;
import com.df.masterdata.entity.Store;

@Transactional
public class StoreDaoTest extends MasterDataJPABaseTest {

	@Inject
	private StoreDao storeDao;

	@Test
	public void testCreateFindAndRemove() {
		Store store = new Store();
		store.setCode("MyCode");
		store.setName("DanceQueue");
		store.setTelephone1("0731-34343433");
		store.setBusinessHourFrom(8);
		store.setBusinessHourFrom(22);
		store.setAddress("china");
		storeDao.newStore(store);
		storeDao.getEntityManager().flush();
		Store found = storeDao.getStoreByCode(store.getCode());
		TestCase.assertNotNull(found);
		storeDao.remove(found);
		found = storeDao.getStoreByCode(store.getCode());
		TestCase.assertNull(found);
	}

	@Test
	public void testFindStoreByName() {
		Store store = new Store();
		String name = "DanceQueue";
		store.setCode("MyCode");
		store.setName(name);
		store.setTelephone1("0731-34343433");
		store.setBusinessHourFrom(8);
		store.setBusinessHourFrom(22);
		store.setAddress("Mawang dui");
		storeDao.newStore(store);
		Store found = storeDao.getStoreByName(name);
		TestCase.assertNotNull(found);
	}

	@Test
	public void testFindStoreByCode() {
		Store store = new Store();
		String code = "MyCode";
		store.setCode(code);
		store.setName("DanceQueue");
		store.setTelephone1("0731-34343433");
		store.setAddress("Mawang dui");
		storeDao.newStore(store);
		Store found = storeDao.getStoreByCode(code);
		TestCase.assertNotNull(found);
	}

	@Test
	public void testGetStoreList() {
		Store store = new Store();
		store.setCode("MyCode");
		store.setName("DanceQueue");
		store.setTelephone1("0731-34343433");
		store.setAddress("AAAA");
		storeDao.newStore(store);
		Store store2 = new Store();
		store2.setCode("MyCode2");
		store2.setName("DanceQueue2");
		store2.setAddress("AAAA");
		store2.setTelephone1("0731-34343433");
		storeDao.newStore(store2);
		Store store3 = new Store();
		store3.setCode("MyCode3");
		store3.setName("DanceQueue3");
		store3.setAddress("AAAA");
		store3.setEnabled(false);
		store3.setTelephone1("0731-34343433");
		storeDao.newStore(store3);
		TestCase.assertEquals(storeDao.all().size(), 2);
	}
}

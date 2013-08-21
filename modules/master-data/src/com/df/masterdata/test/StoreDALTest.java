package com.df.masterdata.test;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.StoreDAL;
import com.df.masterdata.entity.Address;
import com.df.masterdata.entity.Store;

@Transactional
public class StoreDALTest extends MasterDataJPABaseTest {

    @Inject
    private StoreDAL storeDAL;

    @Test
    public void testCreateFindAndRemove() {
	Store store = new Store();
	store.setCode("MyCode");
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
	store.setAddress(address);
	storeDAL.newStore(store);
	storeDAL.getEntityManager().flush();
	Store found = storeDAL.find(Store.class, store.getId());
	TestCase.assertNotNull(found);
	storeDAL.remove(found);
	found = storeDAL.find(Store.class, store.getId());
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
	Address address = new Address();
	address.setCountry("China");
	address.setProvince("Hunan");
	address.setCity("Changsha");
	address.setCounty("HuRong District");
	address.setAddress("Mawang dui");
	store.setAddress(address);
	storeDAL.newStore(store);
	Store found = storeDAL.getStoreByName(name);
	TestCase.assertNotNull(found);
    }

    @Test
    public void testFindStoreByCode() {
	Store store = new Store();
	String code = "MyCode";
	store.setCode(code);
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
	store.setAddress(address);
	storeDAL.newStore(store);
	Store found = storeDAL.getStoreByCode(code);
	TestCase.assertNotNull(found);
    }

    @Test
    public void testGetStoreList() {
	Store store = new Store();
	store.setCode("MyCode");
	store.setName("DanceQueue");
	store.setTelephone1("0731-34343433");
	store.setBusinessHourFrom(8);
	store.setBusinessHourFrom(22);
	storeDAL.newStore(store);
	Store store2 = new Store();
	store2.setCode("MyCode2");
	store2.setName("DanceQueue2");
	store2.setTelephone1("0731-34343433");
	store2.setBusinessHourFrom(8);
	store2.setBusinessHourFrom(22);
	storeDAL.newStore(store2);
	Store store3 = new Store();
	store3.setCode("MyCode3");
	store3.setName("DanceQueue3");
	store3.setEnabled(false);
	store3.setTelephone1("0731-34343433");
	store3.setBusinessHourFrom(8);
	store3.setBusinessHourFrom(22);
	storeDAL.newStore(store3);
	TestCase.assertEquals(storeDAL.getStoreList(false).size(), 2);
    }
}

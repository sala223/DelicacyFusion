package com.df.client.rs.resource.test;

import android.test.suitebuilder.annotation.MediumTest;

import com.df.client.rs.model.Store;
import com.df.client.rs.resource.StoreResource;
import com.google.gson.Gson;

public class StoreResourceTest extends AbstractResourceTest {

    @MediumTest
    public void testGetCategories() {
	StoreResource storeResource = this.getClient().getResource(StoreResource.class);
	Store[] stores = storeResource.getStores();
	Gson gson = new Gson();
	this.logInfo(gson.toJson(stores));
    }

}

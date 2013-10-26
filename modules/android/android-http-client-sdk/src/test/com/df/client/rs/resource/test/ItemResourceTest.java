package com.df.client.rs.resource.test;

import android.test.suitebuilder.annotation.MediumTest;

import com.df.client.rs.model.Item;
import com.df.client.rs.resource.ItemResource;
import com.google.gson.Gson;

public class ItemResourceTest extends AbstractResourceTest {

    @MediumTest
    public void testGetFoods() {
	ItemResource itemResource = this.getClient().getResource(ItemResource.class);
	Item[] items = itemResource.getFoods();
	Gson gson = new Gson();
	this.logInfo(gson.toJson(items));
    }

    @MediumTest
    public void testGetItems() {
	ItemResource itemResource = this.getClient().getResource(ItemResource.class);
	Item[] items = itemResource.getItems();
	Gson gson = new Gson();
	this.logInfo(gson.toJson(items));
    }

    @MediumTest
    public void testGetFoodsByCategory() {
	ItemResource itemResource = this.getClient().getResource(ItemResource.class);
	Item[] items = itemResource.getFoodsByCategory("SYS00002");
	Gson gson = new Gson();
	this.logInfo(gson.toJson(items));
    }
}

package com.df.client.rs.resource.test;

import android.test.suitebuilder.annotation.MediumTest;

import com.df.client.rs.resource.ItemResource;

public class ItemResourceTest extends AbstractResourceTest {

    @MediumTest
    public void testGetFoods() {
	ItemResource itemResource = this.getClient().getResource(ItemResource.class);
	itemResource.getFoods();
    }
}

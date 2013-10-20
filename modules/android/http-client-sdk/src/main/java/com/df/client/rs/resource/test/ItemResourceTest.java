package com.df.client.rs.resource.test;

import org.junit.Test;

import com.df.client.rs.resource.ItemResource;

public class ItemResourceTest extends AbstractResourceTest {

    @Test
    public void getFoods() {
	ItemResource itemResource = this.getClient().getResource(ItemResource.class);
	itemResource.getFoods();
    }
}

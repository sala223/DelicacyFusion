package com.df.client.rs.resource;

import java.io.InputStream;

import com.df.client.rs.model.Item;

public interface ItemResource extends Resource {

	public Item[] getItems();

	public Item[] getFoods();

	public Item[] getFoodsByCategory(String categoryCode);

	public InputStream getItemImage(Item item, String imageId);

}

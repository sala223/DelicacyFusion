package com.df.client.rs.resource;

import com.df.client.rs.model.Item;

public interface ItemResource extends Resource {

	public Item[] getItems();

	public Item[] getFoods();

	public Item[] getFoodsByCategory(String categoryCode);

	public byte[] getItemImage(Item item, String imageId);
}

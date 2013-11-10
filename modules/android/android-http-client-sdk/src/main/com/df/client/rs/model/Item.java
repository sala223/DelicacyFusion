package com.df.client.rs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Item extends MasterData {

	private String name;

	private ItemType type;

	private List<String> categories;

	private Set<PictureRef> pictureSet;

	private String description;

	private float price;

	private String currency;

	private ItemUnit itemUnit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public Set<PictureRef> getPictureSet() {
		return pictureSet;
	}

	public void setPictureSet(Set<PictureRef> pictureSet) {
		this.pictureSet = pictureSet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ItemUnit getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(ItemUnit itemUnit) {
		this.itemUnit = itemUnit;
	}

	public void setCategories(String... categories) {
		if (this.categories == null) {
			this.categories = new ArrayList<String>();
		} else {
			this.categories.clear();
		}
		addCategories(categories);
	}

	public void addCategories(String... categories) {
		if (this.categories == null) {
			this.categories = new ArrayList<String>();
		}
		if (categories != null) {
			for (String c : categories) {
				this.categories.add(c);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Item))
			return false;

		Item another = (Item) o;

		return getCode().equals(another.getCode());
	}
}

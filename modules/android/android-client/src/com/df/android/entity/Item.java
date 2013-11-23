package com.df.android.entity;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private String code;
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	private String name;

	private List<String> categories = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCategories() {
		return categories;
	}

	
	private String image;
	
	private float price;

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Item))
			return false;

		Item another = (Item) o;

		return getCode().equals(another.getCode());
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}

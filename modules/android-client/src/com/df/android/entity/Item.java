package com.df.android.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {
	public enum ItemType {
	    Food, Service, Goods, Other
	}
	
	private String code;
	private String name;
	private ItemType type;
	private float price;
	public void setPrice(float price) {
		this.price = price;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private float actualPrice;
	private String image;
	
	public String getImage() {
		return image;
	}

	private List<ItemCategory> categories = new ArrayList<ItemCategory>();

	protected Item(String code, String name, ItemType type) {
		this(code, name, type, null);
	}
	
	protected Item(String code, String name, ItemType type, String image) {
		this.code = code;
		this.name = name;
		this.type = type;
		this.image = image;
	}
	
    public List<ItemCategory> getCategories() {
		return categories;
	}

	public float getPrice() {
		return price;
	}

	public float getActualPrice() {
		return actualPrice;
	}

	public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void addCategory(ItemCategory category) {
    	categories.add(category);
    }

    public ItemType getType() {
	return type;
    }

    public void setType(ItemType type) {
	this.type = type;
    }
    
    @Override
    public boolean equals(Object o) {
    	if(o == null || !(o instanceof Item))
    		return false;
    	
    	Item another = (Item)o;
    	
    	return code.equals(another.getCode()) && name.equals(another.getName()) && type.equals(another.getType());
    }
    
    public String toString() {
		String ret = "Item{";
		
		ret += "code:" + code + ",";
		ret += "name:" + name + ",";
		ret += "type:" + type;
		
		ret += "}";
		
		return ret;
	}
}


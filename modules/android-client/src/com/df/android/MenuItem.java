package com.df.android;

public class MenuItem {
	public static enum MenuItemType {
		MIT_COLDDISH,
		MIT_HOTDISH,
		MIT_DRINK,
		MIT_DESERT
	}
	public final static MenuItemType dishTypes[] = {
		MenuItemType.MIT_COLDDISH,
		MenuItemType.MIT_HOTDISH,
		MenuItemType.MIT_DRINK,
		MenuItemType.MIT_DESERT };
	
	private String name;
	private String image;
	private MenuItemType type;
	private float price;
	
	public MenuItem(String name, MenuItemType type, String image) {
		this(name, type, 0.00f, image);
	}
	
	public MenuItem(String name, MenuItemType type, float price, String image) {
		this.name = name;
		this.type = type;
		this.price = price;
		this.image = image;
	}
	
	public String getImage() {
		return image;
	} 
	
	public MenuItemType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public float getPrice() {
		return price;
	}
	
	public boolean equals(MenuItem o) {
		if(o == null)
			return false;
		
		return name.equals(o.getName()) && type == o.getType() && image.equals(o.getImage());
	}
}

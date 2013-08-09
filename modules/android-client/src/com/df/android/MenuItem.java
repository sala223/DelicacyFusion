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
	
	public MenuItem(String name, MenuItemType type, String image) {
		this.name = name;
		this.type = type;
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
	
	
}

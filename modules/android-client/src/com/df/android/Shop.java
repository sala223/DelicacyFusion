package com.df.android;

public class Shop {
	private String id;
	private String name;
	private String address;
	private Menu menu;
	
	public Shop(String id, String name) {
		this(id, name, "");
	}
	
	public Shop(String id, String name, String address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public Menu getMenu() {
		return menu;
	}
}

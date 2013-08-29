package com.df.android.entity;

import java.util.ArrayList;
import java.util.List;

import com.df.android.menu.Menu;

public class Shop {
	private String id;
	private String name;
	private String address;
	private Menu menu;
	private List<Table> tables; 
	
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
	
	public void setTables(List<Table> tables) {
		this.tables = tables;
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
	
	public List<Table> getTables() {
		return tables;
	}
	
	private List<ItemCategory> navigatableMenuItemCategories = new ArrayList<ItemCategory>();

	public void setNavigatableMenuItemCategories(
			List<ItemCategory> navigatableMenuItemCategories) {
		this.navigatableMenuItemCategories = navigatableMenuItemCategories;
	}

	public List<ItemCategory> getNavigatableMenuItemCategories() {
		return navigatableMenuItemCategories;
	}
	
}

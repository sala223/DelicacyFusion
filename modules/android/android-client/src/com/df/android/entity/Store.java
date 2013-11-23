package com.df.android.entity;

import java.util.ArrayList;
import java.util.List;

import com.df.android.menu.Menu;

public class Store {
	private String code;
	private String name;
	private String address;
	private Menu menu = new Menu();
	private List<Floor> floors = new ArrayList<Floor>(); 
	
	Store() {}
	
	public Store(String code) {
		this.code = code;
	}
	
	public Store(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
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
	
	public List<Floor> getFloors() {
		return floors;
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

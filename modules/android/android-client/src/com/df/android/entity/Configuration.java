package com.df.android.entity;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
	private List<ItemCategory> navigationCategories = new ArrayList<ItemCategory>();
	
	public List<ItemCategory> getNavigationCategories() {
		return navigationCategories;
	}
	
	public void setNavigationCategories(List<ItemCategory> navigationCategories) {
		this.navigationCategories = navigationCategories;
	}
}

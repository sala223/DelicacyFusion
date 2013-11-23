package com.df.android.menu;

import java.util.HashMap;
import java.util.Map;

import com.df.android.entity.ItemCategory;

public class CategoryDictionary {
	private Map<String, ItemCategory> categoryDictionary = new HashMap<String, ItemCategory>();
	
	public void clear() {
		categoryDictionary.clear();
	}
	
	public int size() {
		return categoryDictionary.size();
	}
	
	public void addCategory(ItemCategory category) {
		categoryDictionary.put(category.getCode(), category);
	}

	public ItemCategory getCategoryByCode(String code) {
		return categoryDictionary.get(code);
	}
	
}

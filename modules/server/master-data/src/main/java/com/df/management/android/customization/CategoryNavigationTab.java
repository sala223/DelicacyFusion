package com.df.management.android.customization;

import java.io.Serializable;

public class CategoryNavigationTab implements Serializable {

	private static final long serialVersionUID = 1L;

	private String category;

	CategoryNavigationTab() {
	}

	public CategoryNavigationTab(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		return category.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return category.equals(obj);
	}

	@Override
	public String toString() {
		return category;
	}
}

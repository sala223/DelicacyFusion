package com.df.management.android.customization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.df.masterdata.auxiliary.Category;

public class CategoryNavigationTabs implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CategoryNavigationTab> tabs = new ArrayList<CategoryNavigationTab>();

	CategoryNavigationTabs() {
	}

	public CategoryNavigationTabs(CategoryNavigationTab... tabs) {
		if (tabs != null) {
			for (CategoryNavigationTab tab : tabs) {
				this.tabs.add(tab);
			}
		}
	}

	public void addTab(CategoryNavigationTab tab) {
		if (!tabs.contains(tab)) {
			tabs.add(tab);
		}
	}

	public List<CategoryNavigationTab> getTabs() {
		return tabs;
	}

	public static class CategoryNavigationTab implements Serializable {

		private static final long serialVersionUID = 1L;

		private String categoryCode;

		private String display;

		CategoryNavigationTab() {
		}

		public CategoryNavigationTab(Category category) {
			this.categoryCode = category.getCode();
			this.display = category.getName();
		}

		public String getCategoryCode() {
			return categoryCode;
		}

		public String getDisplay() {
			return display;
		}

		@Override
		public int hashCode() {
			return categoryCode.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return categoryCode.equals(obj);
		}

		@Override
		public String toString() {
			return categoryCode;
		}
	}
}

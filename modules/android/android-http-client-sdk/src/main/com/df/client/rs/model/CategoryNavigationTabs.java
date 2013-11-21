package com.df.client.rs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

		public void setCategoryCode(String categoryCode) {
			this.categoryCode = categoryCode;
		}

		public void setDisplay(String display) {
			this.display = display;
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

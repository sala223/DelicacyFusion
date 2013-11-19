package com.df.management.android.customization;

import java.util.ArrayList;
import java.util.List;

import com.df.management.configuration.Domain;
import com.df.management.configuration.JsonObjectConfigurable;

public class CategoryNavigationTabs extends JsonObjectConfigurable {

	private List<CategoryNavigationTab> tabs;

	private static final String ANDROID_NAVIGATION_TABS = "ANDROID_NAVIGATION_TABS";

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
		if (tabs == null) {
			tabs = new ArrayList<CategoryNavigationTab>();
		}
		if (!tabs.contains(tab)) {
			tabs.add(tab);
		}
	}

	@Override
	public Domain getDomain() {
		return Domain.ANDROID_UI;
	}

	@Override
	public String getConfigurationKey() {
		return ANDROID_NAVIGATION_TABS;
	}

	@Override
	protected void mappingProperties(Object value) {
		if (value != null && value instanceof CategoryNavigationTabs) {
			CategoryNavigationTabs other = (CategoryNavigationTabs) value;
			this.tabs = other.tabs;
		}
	}
}

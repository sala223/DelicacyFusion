package com.df.management.android.customization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonMappingException;

import com.df.management.configuration.Domain;
import com.df.management.configuration.JsonObjectConfigurable;

public class NavigationTabsDescriptor extends JsonObjectConfigurable {

	public static final String CONFIGURABLE_KEY = "ANDROID_NAVIGATION_TABS";

	public static final Domain CONFIGURABLE_DOMAIN = Domain.ANDROID_UI;

	private List<TabDescriptor> tabs = new ArrayList<TabDescriptor>();

	public void addTabDescriptor(TabDescriptor tab) {
		tabs.add(tab);
	}

	public void addTabDescriptor(String category) {
		tabs.add(new TabDescriptor(category));
	}

	public List<TabDescriptor> getTabs() {
		return tabs;
	}

	@Override
	@JsonIgnore
	public Domain getDomain() {
		return CONFIGURABLE_DOMAIN;
	}

	@Override
	@JsonIgnore
	public String getConfigurationKey() {
		return CONFIGURABLE_KEY;
	}

	@Override
	protected void mappingProperties(Object value) {
		if (value != null && value instanceof NavigationTabsDescriptor) {
			NavigationTabsDescriptor other = (NavigationTabsDescriptor) value;
			this.tabs = other.tabs;
		}
	}

	@Override
	public String marshall() throws JsonGenerationException, JsonMappingException, IOException {
		Collections.sort(tabs);
		return super.marshall();
	}

	@Override
	protected void initWithDefault() {
	}

	public static class TabDescriptor implements Comparator<TabDescriptor>, Comparable<TabDescriptor> {
		private String categoryCode;

		private int index;

		TabDescriptor() {
		}

		public TabDescriptor(String categoryCode) {
			this.categoryCode = categoryCode;
		}

		public TabDescriptor(String categoryCode, int index) {
			this.categoryCode = categoryCode;
			this.index = index;
		}

		public String getCategoryCode() {
			return categoryCode;
		}

		public void setCategoryCode(String categoryCode) {
			this.categoryCode = categoryCode;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		@Override
		public int compare(TabDescriptor t1, TabDescriptor t2) {
			return t1.getIndex() - t2.getIndex();
		}

		@Override
		public int compareTo(TabDescriptor other) {
			return this.getIndex() - other.getIndex();
		}
	}
}

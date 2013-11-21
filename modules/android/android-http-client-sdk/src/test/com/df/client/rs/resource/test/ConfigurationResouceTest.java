package com.df.client.rs.resource.test;

import android.test.suitebuilder.annotation.MediumTest;

import com.df.client.rs.model.CategoryNavigationTabs;
import com.df.client.rs.resource.ConfigurationResource;
import com.google.gson.Gson;

public class ConfigurationResouceTest extends AbstractResourceTest {
	@MediumTest
	public void testGetCategoryNavigationTabs() {
		ConfigurationResource configurationResource = this.getClient().getResource(ConfigurationResource.class);
		CategoryNavigationTabs tabs = configurationResource.getCategoryNavigationTabs();
		Gson gson = new Gson();
		this.logInfo(gson.toJson(tabs));
	}
}

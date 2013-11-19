package com.df.management.configutation.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.management.android.customization.NavigationTabsDescriptor;
import com.df.management.configuration.Configurator;
import com.df.masterdata.test.MasterDataJPABaseTest;

@Transactional
public class ConfiguratorTest extends MasterDataJPABaseTest {

	@Autowired
	private Configurator configurator;

	@Test
	public void testUpdateCategoryNavitationTabs() {
		NavigationTabsDescriptor tabs = new NavigationTabsDescriptor();
		tabs.addTabDescriptor("SYS00001");
		tabs.addTabDescriptor("SYS00002");
		configurator.updateConfigurable(tabs);
	}

	@Test
	public void testLoadCategoryNavitationTabs() {
		NavigationTabsDescriptor tabs = new NavigationTabsDescriptor();
		tabs.addTabDescriptor("SYS00001");
		tabs.addTabDescriptor("SYS00002");
		configurator.updateConfigurable(tabs);
		NavigationTabsDescriptor found = configurator.getConfigurable(NavigationTabsDescriptor.class, tabs.getDomain(),
		        tabs.getConfigurationKey());
		TestCase.assertEquals(found.getTabs().size(), 2);
	}
}

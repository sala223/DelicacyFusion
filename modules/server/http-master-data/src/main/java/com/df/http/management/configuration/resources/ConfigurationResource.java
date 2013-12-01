package com.df.http.management.configuration.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantLevelResource;
import com.df.management.android.customization.CategoryNavigationTabs;
import com.df.management.android.customization.CategoryNavigationTabs.CategoryNavigationTab;
import com.df.management.android.customization.NavigationTabsDescriptor;
import com.df.management.android.customization.NavigationTabsDescriptor.TabDescriptor;
import com.df.management.configuration.Configurator;
import com.df.masterdata.auxiliary.Category;
import com.df.masterdata.auxiliary.CategoryLoader;

@Path("/tenant/{tenantCode}/configuration")
@Produces("application/json;charset=UTF-8")
@Component
public class ConfigurationResource extends TenantLevelResource {

	@Autowired
	private Configurator configurator;

	@Autowired
	private CategoryLoader categoryLoader;

	public void setConfigurator(Configurator configurator) {
		this.configurator = configurator;
	}

	/**
	 * Create or update a category navigation tabs for a tenant.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 */
	@PUT
	@Path("/cnv")
	public void createCategoryNavigationTabs(@PathParam("tenantCode") String tenantCode,
	        NavigationTabsDescriptor descriptor) {
		injectTenantContext(tenantCode);
		NavigationTabsDescriptor tmp = new NavigationTabsDescriptor();
		for (TabDescriptor tab : descriptor.getTabs()) {
			tmp.addTabDescriptor(tab);
		}
		configurator.updateConfigurable(tmp);
	}

	@GET
	@Path("/cnv")
	public CategoryNavigationTabs getCategoryNavigationTabs(@PathParam("tenantCode") String tenantCode) {
		injectTenantContext(tenantCode);
		NavigationTabsDescriptor descriptor = configurator.getConfigurable(NavigationTabsDescriptor.class,
		        NavigationTabsDescriptor.CONFIGURABLE_DOMAIN, NavigationTabsDescriptor.CONFIGURABLE_KEY);
		List<TabDescriptor> tabDescriptors = descriptor.getTabs();
		CategoryNavigationTabs tabs = new CategoryNavigationTabs();
		for (TabDescriptor tabDescriptor : tabDescriptors) {
			Category category = categoryLoader.getCategory(tabDescriptor.getCategoryCode());
			if (category != null) {
				tabs.addTab(new CategoryNavigationTab(category));
			}
		}
		return tabs;
	}
}

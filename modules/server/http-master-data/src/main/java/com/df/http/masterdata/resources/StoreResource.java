package com.df.http.masterdata.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Store;
import com.df.masterdata.service.contract.StoreService;

@Path("/tenant/{tenantCode}/store")
@Produces("application/json;charset=UTF-8")
@Component
public class StoreResource extends TenantResource {

	@Inject
	private StoreService storeService;

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	/**
	 * Get all stores in a tenant
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @return all the stores for a tenant
	 */
	@GET
	@Path("/")
	public Store[] getStores(@PathParam("tenantCode") String tenantCode) {
		injectTenantContext(tenantCode);
		return storeService.getStoreList().toArray(new Store[0]);
	}

	/**
	 * Get store by store code.
	 * 
	 * @param tenantCode
	 *            the tenant code
	 * @param storeCode
	 *            the store code
	 * @return return the found store or return null if it doesn't exist.
	 */
	@GET
	@Path("/{storeCode}")
	public Store getStoreByCode(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode) {
		injectTenantContext(tenantCode);
		return storeService.getStoreByCode(storeCode);
	}

	/**
	 * Create a new store in a tenant.
	 * 
	 * @param tenantCode
	 *            the tenant code
	 * @return return the refreshed new store object.
	 */
	@POST
	@Path("/")
	public Store createStore(@PathParam("tenantCode") String tenantCode, Store store) {
		injectTenantContext(tenantCode);
		storeService.newStore(store);
		return store;
	}

}

package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

import com.df.core.common.context.TenantContext;
import com.df.core.common.context.TenantContextHolder;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Store;
import com.df.masterdata.service.inf.ItemServiceInf;
import com.df.masterdata.service.inf.StoreServiceInf;

@Path("/{tenantId}/store")
@Produces("application/json")
@Component
public class StoreResource {
    @Inject
    private ItemServiceInf itemService;
    @Inject
    private StoreServiceInf storeService;

    @GET
    @Path("/")
    public List<Store> getStores(@PathParam("tenantId") String tenantId) {
	injectTenantContext(tenantId);
	return storeService.getStoreList();
    }

    @GET
    @Path("/{storeId}/{categoryId}/food")
    public List<Item> getFoodsByCategory(@PathParam("tenantId") String tenantId, @PathParam("storeId") long storeId,
	    @PathParam("categoryId") long categoryId) {
	injectTenantContext(tenantId);
	return itemService.getFoodsByCategory(storeId, categoryId);
    }

    @GET
    @Path("/{storeId}/food/count")
    public long getFoodsCount(@PathParam("tenantId") String tenantId, @PathParam("storeId") long storeId) {
	injectTenantContext(tenantId);
	return itemService.getAvaliableFoodCount(storeId);
    }

    @GET
    @Path("/{storeId}/food")
    public List<Item> getFoods(@PathParam("tenantId") String tenantId, @PathParam("storeId") long storeId,
	    @QueryParam("from") @DefaultValue("0") int from, @QueryParam("to") @DefaultValue("100") int to) {
	injectTenantContext(tenantId);
	int firstResult = from < 0 ? 0 : from;
	return itemService.getAvaliableFoods(storeId, firstResult, to);
    }

    private void injectTenantContext(String tenantId) {
	TenantContext context = new TenantContext(tenantId);
	TenantContextHolder.setTenant(context);
    }
}

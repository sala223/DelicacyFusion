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

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Item;
import com.df.masterdata.service.contract.ItemServiceInf;

@Path("/{tenantId}/{storeCode}/item")
@Produces("application/json")
@Component
public class ItemResource extends TenantResource {
    @Inject
    private ItemServiceInf itemService;

    public void setItemService(ItemServiceInf itemService) {
	this.itemService = itemService;
    }

    @GET
    @Path("/food")
    public List<Item> getFoodsByCategory(@PathParam("tenantId") String tenantId,
	    @PathParam("storeCode") String storeCode, @QueryParam("categoryCode") String categoryCode) {
	injectTenantContext(tenantId);
	return itemService.getFoodsByCategory(storeCode, categoryCode);
    }

    @GET
    @Path("/food/count")
    public long getFoodsCount(@PathParam("tenantId") String tenantId, @PathParam("storeCode") String storeCode) {
	injectTenantContext(tenantId);
	return itemService.getAvaliableFoodCount(storeCode);
    }

    @GET
    @Path("/food")
    public List<Item> getFoods(@PathParam("tenantId") String tenantId, @PathParam("storeCode") String storeCode,
	    @QueryParam("from") @DefaultValue("0") int from, @QueryParam("to") @DefaultValue("100") int to) {
	injectTenantContext(tenantId);
	int firstResult = from < 0 ? 0 : from;
	return itemService.getAvaliableFoods(storeCode, firstResult, to);
    }
}

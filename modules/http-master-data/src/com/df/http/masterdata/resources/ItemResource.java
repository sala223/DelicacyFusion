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
import com.df.masterdata.service.inf.ItemServiceInf;

@Path("/{tenantId}/{storeId}/item")
@Produces("application/json")
@Component
public class ItemResource extends TenantResource {
    @Inject
    private ItemServiceInf itemService;

    
    public void setItemService(ItemServiceInf itemService) {
        this.itemService = itemService;
    }

    @GET
    @Path("/")
    public List<Item> getFoodsByCategory(@PathParam("tenantId") String tenantId, @PathParam("storeId") long storeId,
	    @QueryParam("categoryId") long categoryId) {
	injectTenantContext(tenantId);
	return itemService.getFoodsByCategory(storeId, categoryId);
    }

    @GET
    @Path("/count")
    public long getFoodsCount(@PathParam("tenantId") String tenantId, @PathParam("storeId") long storeId) {
	injectTenantContext(tenantId);
	return itemService.getAvaliableFoodCount(storeId);
    }

    @GET
    @Path("/")
    public List<Item> getFoods(@PathParam("tenantId") String tenantId, @PathParam("storeId") long storeId,
	    @QueryParam("from") @DefaultValue("0") int from, @QueryParam("to") @DefaultValue("100") int to) {
	injectTenantContext(tenantId);
	int firstResult = from < 0 ? 0 : from;
	return itemService.getAvaliableFoods(storeId, firstResult, to);
    }
}

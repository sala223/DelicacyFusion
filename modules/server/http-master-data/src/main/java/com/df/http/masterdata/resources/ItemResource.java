package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.stereotype.Component;

import com.df.blobstore.image.http.ImageLinkCreator;
import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Item;
import com.df.masterdata.service.contract.ItemService;

@Path("/tenant/{tenantCode}/store/{storeCode}")
@Produces("application/json;charset=UTF-8")
@Component
public class ItemResource extends TenantResource {
    @Inject
    private ItemService itemService;

    @Inject
    private ImageLinkCreator imageLinkCreator;

    public void setItemService(ItemService itemService) {
	this.itemService = itemService;
    }

    /**
     * Get all the food by the specified category in a store.
     * 
     * @param tenantCode
     *            The tenant code
     * @param storeCode
     *            The store code
     * @param categoryCode
     *            The category code
     * @return all the food
     */
    @GET
    @Path("/{categoryCode}/food")
    @TypeHint(Item.class)
    public List<Item> getFoodsByCategory(@PathParam("tenantCode") String tenantCode,
	    @PathParam("storeCode") String storeCode, @PathParam("categoryCode") String categoryCode) {
	injectTenantContext(tenantCode);
	return createItemImageLink(itemService.getFoodsByCategory(storeCode, categoryCode));
    }

    /**
     * Get the food count in a store.
     * 
     * @param tenantCode
     *            The tenant code
     * @param storeCode
     *            The store code
     * @return the food count
     */
    @GET
    @Path("/food/count")
    public long getFoodsCount(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode) {
	injectTenantContext(tenantCode);
	return itemService.getAvaliableFoodCount(storeCode);
    }

    /**
     * Pagination retrieve foods from a store
     * 
     * @param tenantCode
     *            The tenant code
     * @param storeCode
     *            The store code
     * @param from
     *            The offset to begin to retrieve
     * @param to
     *            The offset to end to retrieve
     * @return the foods between <code>from</code> and <code>to</code>.
     */
    @GET
    @Path("/food")
    @TypeHint(Item.class)
    public List<Item> getFoods(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	    @QueryParam("from") @DefaultValue("0") int from, @QueryParam("to") @DefaultValue("100") int to) {
	injectTenantContext(tenantCode);
	int firstResult = from < 0 ? 0 : from;
	return createItemImageLink(itemService.getAvaliableFoods(storeCode, firstResult, to));
    }

    /**
     * Pagination retrieve items from a store
     * 
     * @param tenantCode
     *            The tenant code
     * @param storeCode
     *            The store code
     * @param from
     *            The offset to begin to retrieve
     * @param to
     *            The offset to end to retrieve
     * @return the items between <code>from</code> and <code>to</code>.
     */
    @GET
    @Path("/item")
    @TypeHint(Item.class)
    public List<Item> getItems(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	    @QueryParam("from") @DefaultValue("0") int from, @QueryParam("to") @DefaultValue("100") int to) {
	injectTenantContext(tenantCode);
	int firstResult = from < 0 ? 0 : from;
	return createItemImageLink(itemService.getAvaliableItems(storeCode, firstResult, to));
    }

    protected List<Item> createItemImageLink(List<Item> items) {
	if (items == null) {
	    return items;
	}
	for (Item item : items) {
	    item.createImageLink(imageLinkCreator);
	}
	return items;
    }
}

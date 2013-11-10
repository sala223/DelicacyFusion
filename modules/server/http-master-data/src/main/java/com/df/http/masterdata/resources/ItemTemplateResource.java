package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.stereotype.Component;

import com.df.blobstore.image.http.ImageLinkCreator;
import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.service.contract.ItemTemplateService;

@Path("/tenant/{tenantCode}/itpl/")
@Produces("application/json;charset=UTF-8")
@Component
public class ItemTemplateResource extends TenantResource {

    @Inject
    private ItemTemplateService itemTemplateService;

    @Inject
    private ImageLinkCreator imageLinkCreator;

    public void setItemTemplateDao(ItemTemplateService service) {
	this.itemTemplateService = service;
    }

    /**
     * Create a item template in a tenant.
     * 
     * @param tenantCode
     *            The tenant code
     */
    @POST
    @Path("/")
    public void createItemTemplate(ItemTemplate itpl) {
    }

    /**
     * Update a item template in a tenant.
     * 
     * @param tenantCode
     *            The tenant code
     */
    @PUT
    @Path("/")
    public void updateItemTemplate(ItemTemplate itemTemplateCode) {
	itemTemplateService.updateItemTemplate(itemTemplateCode);
    }

    /**
     * Get all available item templates for a tenant.
     * 
     * @param tenantCode
     *            The tenant code
     * @return all available item template for a tenant.
     */
    @GET
    @Path("/")
    @TypeHint(ItemTemplate.class)
    public ItemTemplate[] getAvaliableItemTemplates(@PathParam("tenantCode") String tenantCode) {
	injectTenantContext(tenantCode);
	List<ItemTemplate> its = itemTemplateService.all(false);
	for (ItemTemplate it : its) {
	    it.createImageLink(imageLinkCreator);
	}
	return its.toArray(new ItemTemplate[0]);
    }
}

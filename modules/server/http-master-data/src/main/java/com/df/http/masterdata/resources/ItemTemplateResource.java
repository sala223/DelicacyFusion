package com.df.http.masterdata.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.jboss.resteasy.util.Base64;
import org.springframework.stereotype.Component;

import com.df.blobstore.image.http.ImageLinkCreator;
import com.df.core.rs.TenantResource;
import com.df.http.masterdata.resources.exception.InputValidationException;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.PictureRef;
import com.df.masterdata.service.contract.ItemTemplateService;

@Path("/tenant/{tenantCode}/itpl/")
@Produces("application/json;charset=UTF-8")
@Component
public class ItemTemplateResource extends TenantResource {

	@Inject
	private ItemTemplateService itplService;

	@Inject
	private ImageLinkCreator imageLinkCreator;

	public void setItemTemplateService(ItemTemplateService service) {
		this.itplService = service;
	}

	/**
	 * Create a item template in a tenant.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 */
	@POST
	@Path("/")
	public void createItemTemplate(@PathParam("tenantCode") String tenantCode, ItemTemplate itpl) {
		this.injectTenantContext(tenantCode);
		itplService.createItemTemplate(itpl);
	}

	/**
	 * Update a item template in a tenant.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 */
	@PUT
	@Path("/")
	public void updateItemTemplate(@PathParam("tenantCode") String tenantCode, ItemTemplate itemTemplate) {
		this.injectTenantContext(tenantCode);
		itplService.updateItemTemplate(itemTemplate);
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
		List<ItemTemplate> its = itplService.all(false);
		for (ItemTemplate it : its) {
			it.createImageLink(imageLinkCreator);
		}
		return its.toArray(new ItemTemplate[0]);
	}

	@PUT
	@Path("/{itplCode}/image")
	public PictureRef updateItemImage(@PathParam("tenantCode") String tenantCode,
	        @PathParam("itplCode") String itplCode, String imageData) {
		this.injectTenantContext(tenantCode);
		byte[] imageBytes;
		try {
			imageBytes = Base64.decode(imageData);
		} catch (IOException ex) {
			int code = InputValidationException.IMAGE_NOT_BASE64_ENCODED;
			throw new InputValidationException(code, ex.getMessage());
		}
		PictureRef pictureRef = itplService.uploadItemPicture(itplCode, new ByteArrayInputStream(imageBytes), null);
		String link = imageLinkCreator.createImageLink(pictureRef.getImageId());
		pictureRef.setImageLink(link);
		return pictureRef;
	}

	@DELETE
	@Path("/{itplCode}/image/{imageId}")
	public void deleteItemImage(@PathParam("tenantCode") String tenantCode, @PathParam("itplCode") String itplCode,
	        @PathParam("imageId") String imageId) {
		this.injectTenantContext(tenantCode);
		itplService.removeItemPicture(itplCode, imageId);
	}

}

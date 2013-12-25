package com.df.http.masterdata.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.util.Base64;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.http.ImageReference;
import com.df.blobstore.image.http.ImageReferenceFactory;
import com.df.core.rs.TenantLevelResource;
import com.df.http.masterdata.resources.exception.InputValidationException;
import com.df.masterdata.entity.Store;
import com.df.masterdata.service.contract.StoreService;

@Path("/tenant/{tenantCode}/store")
@Produces("application/json;charset=UTF-8")
@Component
public class StoreResource extends TenantLevelResource {

	@Inject
	private StoreService storeService;

	@Inject
	private ImageReferenceFactory imageReferenceFactory;

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public void setImageReferenceFactory(ImageReferenceFactory imageReferenceFactory) {
		this.imageReferenceFactory = imageReferenceFactory;
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
		Store[] stores = storeService.getStoreList().toArray(new Store[0]);
		for (Store store : stores) {
			if (store.getImageId() != null) {
				ImageKey key = new ImageKey(store.getImageId());
				store.setImage(imageReferenceFactory.createImageReference(key));
			}
		}
		return stores;
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
	@Path("/{storeCode}")
	@GET
	public Store getStoreByCode(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode) {
		injectTenantContext(tenantCode);
		Store store = storeService.getStoreByCode(storeCode);
		if (!StringUtils.isEmpty(store.getImageId())) {
			ImageKey key = new ImageKey(store.getImageId());
			store.setImage(imageReferenceFactory.createImageReference(key));
		}
		return store;
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
	@PreAuthorize("hasTenantRole(#tenantCode,'TENANT_ADMIN')")
	public Store createStore(@PathParam("tenantCode") String tenantCode, Store store) {
		injectTenantContext(tenantCode);
		storeService.newStore(store);
		return store;
	}

	/**
	 * Create a new store in a tenant.
	 * 
	 * @param tenantCode
	 *            the tenant code
	 * @return return the refreshed new store object.
	 */
	@PUT
	@Path("/")
	@PreAuthorize("hasTenantRole(#tenantCode,'TENANT_ADMIN')")
	public Store updateStore(@PathParam("tenantCode") String tenantCode, Store store) {
		injectTenantContext(tenantCode);
		storeService.updateStore(store);
		return store;
	}

	@PUT
	@Path("{storeCode}/image")
	@PreAuthorize("hasTenantRole(#tenantCode,'TENANT_ADMIN')")
	public ImageReference updateStoreImage(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, String imageData) {
		this.injectTenantContext(tenantCode);
		byte[] imageBytes;
		try {
			imageBytes = Base64.decode(imageData);
		} catch (IOException ex) {
			int code = InputValidationException.IMAGE_NOT_BASE64_ENCODED;
			throw new InputValidationException(code, ex.getMessage());
		}
		ImageKey key = storeService.updateStoreImage(storeCode, new ByteArrayInputStream(imageBytes));
		return imageReferenceFactory.createImageReference(key);
	}
}

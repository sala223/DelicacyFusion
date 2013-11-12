package com.df.http.masterdata.resources;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.df.blobstore.image.Image;
import com.df.blobstore.image.ImageFormat;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageService;
import com.df.blobstore.image.http.ImageLinkCreator;
import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.PictureRef;
import com.df.masterdata.exception.ItemException;
import com.df.masterdata.service.contract.ItemService;

@Path("/tenant/{tenantCode}/store/{storeCode}")
@Produces("application/json;charset=UTF-8")
@Component
public class ItemResource extends TenantResource {
	@Inject
	private ItemService itemService;

	@Inject
	private ImageLinkCreator imageLinkCreator;

	@Inject
	private ImageService imageService;

	private static final Logger logger = LoggerFactory.getLogger(ItemResource.class);

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

	@GET
	@Path("/item/{itemCode}/image/{imageId}")
	/**
	 * Get item image by image id.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param itemCode
	 *            The item code
	 * @param imageId
	 *            The id of the image to be retrieved.
	 * @return the items between <code>from</code> and <code>to</code>.
	 */
	public Response getItemImage(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        @PathParam("itemCode") String itemCode, @PathParam("imageId") String imageId) {
		injectTenantContext(tenantCode);
		Item item = itemService.getItemByCode(storeCode, itemCode);
		if (item == null) {
			throw ItemException.itemWithCodeNotExist(itemCode);
		}
		for (ImageFormat format : ImageFormat.values()) {
			if (imageId.endsWith("." + format.name().toLowerCase())) {
				imageId = imageId.substring(0, imageId.length() - format.name().length() - 1);
				break;
			}
		}

		PictureRef pic = item.getImageByImageId(imageId);
		if (pic == null) {
			return Response.status(Status.NOT_FOUND).type(MediaType.IMAGE_JPEG_VALUE).build();
		}
		InputStream in = null;
		try {
			Image image = imageService.fetchImage(new ImageKey(pic.getImageId()));
			ImageFormat format = image.getImageAttributes().getFormat();
			ResponseBuilder builder = Response.ok();
			builder.type(format.getMediaType());
			in = image.getBundleValue().getDataInBundle();
			byte[] imageData = new byte[image.getBundleValue().getSize()];

			in.read(imageData, 0, imageData.length);
			return builder.entity(imageData).build();
		} catch (Throwable ex) {
			String msg = "Cannot get image from key " + pic.getImageId();
			logger.error(msg, ex);
			return Response.status(Status.NOT_FOUND).type(MediaType.IMAGE_JPEG_VALUE).build();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Throwable ex) {
				}
			}
		}
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

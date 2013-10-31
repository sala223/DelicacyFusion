package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.df.blobstore.image.http.ImageLinkCreator;
import com.df.core.rs.TenantResource;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.ItemTemplate;

@Path("/tenant/{tenantCode}/itpl/")
@Produces("application/json;charset=UTF-8")
@Component
public class ItemTemplateResource extends TenantResource {

    @Inject
    private ItemTemplateDao itemTemplateDao;

    @Inject
    private ImageLinkCreator imageLinkCreator;

    public void setItemTemplateDao(ItemTemplateDao itemTemplateDao) {
	this.itemTemplateDao = itemTemplateDao;
    }

    public void createItemTemplate(ItemTemplate itpl) {
	itemTemplateDao.newItemTemplate(itpl);
    }

    @GET
    @Path("/")
    public List<ItemTemplate> getAvaliableItemTemplates(@PathParam("tenantCode") String tenantCode) {
	injectTenantContext(tenantCode);
	List<ItemTemplate> its = itemTemplateDao.all(ItemTemplate.class, 0, Integer.MAX_VALUE, false);
	for (ItemTemplate it : its) {
	    it.createImageLink(imageLinkCreator);
	}
	return its;
    }
}

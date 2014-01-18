package com.df.http.masterdata.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantLevelResource;
import com.df.masterdata.entity.Promotion;
import com.df.masterdata.entity.Promotion.PromotionType;
import com.df.masterdata.service.contract.PromotionService;

@Path("/tenant/{tenantCode}/store/{storeCode}/promotion")
@Produces("application/json;charset=UTF-8")
@Component
public class PromotionResource extends TenantLevelResource {

	@Autowired
	private PromotionService promotionService;

	public void setPromotionService(PromotionService promotionService) {
		this.promotionService = promotionService;
	}

	@POST
	@Path("/")
	@TypeHint(Promotion.class)
	public Promotion createPromotion(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, Promotion promotion) {
		this.injectTenantContext(tenantCode);
		return promotionService.createPromotion(storeCode, promotion);
	}

	@PUT
	@Path("/")
	@TypeHint(Promotion.class)
	public Promotion updatePromotion(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, Promotion promotion) {
		this.injectTenantContext(tenantCode);
		return promotionService.updatePromotion(storeCode, promotion);
	}

	@GET
	@Path("/")
	@TypeHint(Promotion.class)
	public List<Promotion> getPromotionByType(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, @QueryParam("type") PromotionType type) {
		this.injectTenantContext(tenantCode);
		return promotionService.getPromotionsByType(storeCode, type);
	}
}

package com.df.http.order.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantLevelResource;
import com.df.order.entity.ServiceCard;
import com.df.order.table.occupation.DiningTableDistributor;

@Path("/tenant/{tenantCode}/store/{storeCode}/table")
@Produces("application/json;charset=UTF-8")
@Component
public class TableOccupationResource extends TenantLevelResource {

	@Autowired
	private DiningTableDistributor diningTableDistributor;

	public void setDiningTableDistributor(DiningTableDistributor diningTableDistributor) {
		this.diningTableDistributor = diningTableDistributor;
	}

	@GET
	@Path("/occupation")
	@TypeHint(ServiceCard.class)
	public List<ServiceCard> getTableOccupation(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode) {
		this.injectTenantContext(tenantCode);
		return diningTableDistributor.getTableOccupation(storeCode);
	}

	@GET
	@Path("/avaliable")
	public List<String> getAvaliableTables(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode) {
		this.injectTenantContext(tenantCode);
		return diningTableDistributor.getAvaliableTables(storeCode);
	}

	@POST
	@Path("/acquire")
	public ServiceCard acquireTables(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode, List<String> tables) {
		this.injectTenantContext(tenantCode);
		return diningTableDistributor.acquireTables(storeCode, tables);
	}

	@DELETE
	@Path("/{serviceCardId}")
	public void releaseTables(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        @PathParam("serviceCardId") long serviceCardId) {
		this.injectTenantContext(tenantCode);
		diningTableDistributor.releaseTables(storeCode, serviceCardId);
	}

}

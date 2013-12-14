package com.df.http.crm.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.crm.entity.Tenant;
import com.df.crm.service.contract.TenantService;

@Path("/tenant")
@Produces("application/json;charset=UTF-8")
@Component
public class TenantResource {

	@Autowired
	private TenantService tenantService;

	@POST
	@Path("/")
	public Tenant createTenant(Tenant tenant) {
		tenantService.createTenant(tenant);
		return tenant;
	}

	@PUT
	@Path("/")
	public Tenant updateTenant(Tenant tenant) {
		tenantService.updateTenant(tenant);
		return tenant;
	}

	@GET
	@Path("/")
	public List<Tenant> getTenants(@QueryParam("fromResult") int firstResult, @QueryParam("maxResult") int maxResult) {
		return tenantService.getTenants(firstResult, maxResult);
	}

	@GET
	@Path("/{tenantCode}")
	public Tenant getTenantByCode(@PathParam("tenantCode") String tenantCode) {
		return tenantService.getTenantByCode(tenantCode);
	}
}

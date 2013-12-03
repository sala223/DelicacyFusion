package com.df.http.masterdata.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantLevelResource;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.service.contract.DiningTableService;

@Path("/tenant/{tenantCode}/store/{storeCode}/table")
@Produces("application/json;charset=UTF-8")
@Component
public class DiningTableResource extends TenantLevelResource {

	@Autowired
	private DiningTableService diningTableService;

	public void setDiningTableService(DiningTableService tableService) {
		this.diningTableService = tableService;
	}

	/**
	 * Get all dining tables in a store
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @return all dining tables in the store
	 */
	@GET
	@Path("/")
	@TypeHint(DiningTable.class)
	public List<DiningTable> getDiningTables(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode) {
		this.injectTenantContext(tenantCode);
		return diningTableService.getDiningTables(storeCode);
	}

	/**
	 * Create a dining table in a store.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param table
	 *            The new dining table to be added.
	 */
	@POST
	@Path("/")
	public void createDiningTable(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        DiningTable table) {
		this.injectTenantContext(tenantCode);
		diningTableService.newDiningTable(storeCode, table);
	}

	/**
	 * Update the dining table information
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param table
	 *            The table with the new information to be updated.
	 */
	@PUT
	@Path("/")
	public void updateDiningTale(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        DiningTable table) {
		this.injectTenantContext(tenantCode);
		diningTableService.updateDiningTable(storeCode, table);
	}

	/**
	 * Delete a specified dining table by table code.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param tableCode
	 *            The table code.
	 */
	@DELETE
	@Path("/{tableCode}")
	public void deleteDiningTable(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        String tableCode) {
		this.injectTenantContext(tenantCode);
		diningTableService.removeDiningTableByCode(storeCode, tableCode);
	}
}

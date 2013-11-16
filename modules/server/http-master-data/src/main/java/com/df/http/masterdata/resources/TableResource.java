package com.df.http.masterdata.resources;

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

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.service.contract.TableService;

@Path("/tenant/{tenantCode}/store/{storeCode}/table")
@Produces("application/json;charset=UTF-8")
@Component
public class TableResource extends TenantResource {

	@Autowired
	private TableService tableService;

	/**
	 * Get all tables in a store
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @return all tables.
	 */
	@GET
	@Path("/")
	@TypeHint(DiningTable.class)
	public List<DiningTable> getTables(@PathParam("tenantCode") String tenantCode,
	        @PathParam("storeCode") String storeCode) {
		this.injectTenantContext(tenantCode);
		return tableService.getTables(storeCode);
	}

	/**
	 * Delete a table with specified number in a store
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param tableNumber
	 *            The number of the table which will be deleted.
	 */
	@DELETE
	@Path("/{tableCode}")
	public void deleteTable(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        @PathParam("tableCode") String tableCode) {
		this.injectTenantContext(tenantCode);
		tableService.removeTableByCode(storeCode, tableCode);
	}

	/**
	 * Add a new dining table in a store.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param table
	 *            The new dining table information
	 */
	@POST
	@Path("/")
	public void addTable(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        DiningTable table) {
		this.injectTenantContext(tenantCode);
		tableService.addTable(storeCode, table);
	}
}

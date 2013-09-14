package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Store;
import com.df.masterdata.service.contract.StoreServiceInf;

@Path("/{tenantId}/store")
@Produces("application/json")
@Component
public class StoreResource extends TenantResource {

    @Inject
    private StoreServiceInf storeService;

    
    public void setStoreService(StoreServiceInf storeService) {
        this.storeService = storeService;
    }


    @GET
    @Path("/")
    public List<Store> getStores(@PathParam("tenantId") String tenantId) {
	injectTenantContext(tenantId);
	return storeService.getStoreList();
    }

}

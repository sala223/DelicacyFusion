package com.df.http.order.resources;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.order.entity.Order;
import com.df.order.service.contract.OrderServiceInf;

@Path("/tenant/{tenantId}/{storeCode}/order")
@Produces("application/json;charset=UTF-8")
@Component
public class OrderResource extends TenantResource {

    @Autowired
    private OrderServiceInf orderService;

    public List<Order> getOrders(@PathParam("tenantId") String tenantId, @PathParam("storeCode") String storeCode,
	    @QueryParam("from") @DefaultValue("0") int from, @QueryParam("to") @DefaultValue("100") int to) {
	return null;
    }
}

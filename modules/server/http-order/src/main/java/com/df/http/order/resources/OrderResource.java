package com.df.http.order.resources;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.enunciate.jaxrs.TypeHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.order.entity.Order;
import com.df.order.service.contract.OrderService;

@Path("/tenant/{tenantCode}/store/{storeCode}/order")
@Produces("application/json;charset=UTF-8")
@Component
public class OrderResource extends TenantResource {

    @Autowired
    private OrderService orderService;

    /**
     * Pagination retrieve orders from a store
     * 
     * @param tenantCode
     *            The tenant code
     * @param storeCode
     *            The store code
     * @param from
     *            The offset to begin to retrieve
     * @param to
     *            The offset to end to retrieve
     * @return the orders between <code>from</code> and <code>to</code>.
     */
    @GET
    @TypeHint(Order.class)
    public List<Order> getOrders(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	    @QueryParam("from") @DefaultValue("0") int from, @QueryParam("to") @DefaultValue("100") int to) {
	return null;
    }

    @GET
    @TypeHint(Order.class)
    public List<Order> getLatestOrders(@PathParam("tenantCode") String tenantCode,
	    @PathParam("storeCode") String storeCode, @QueryParam("user") long userId) {
	this.injectTenantContext(tenantCode);
	return orderService.getLatestOrders(userId, 10);
    }

    /**
     * Create a order
     * 
     * @param tenantCode
     *            The tenant code
     * @param storeCode
     *            The store code
     * @param order
     *            The order to be created
     * @return the refreshed order.
     */
    @POST
    public Order createOrder(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	    Order order) {
	this.injectTenantContext(tenantCode);
	order.setStoreCode(storeCode);
	orderService.createOrder(0, order);
	return order;
    }
}

package com.df.http.masterdata.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Room;
import com.df.masterdata.service.contract.RoomServiceInf;

@Path("/tenant/{tenantCode}/store/{storeCode}/room")
@Produces("application/json;charset=UTF-8")
@Component
public class RoomResource extends TenantResource {

    @Inject
    private RoomServiceInf roomService;

    public void setRoomService(RoomServiceInf roomService) {
	this.roomService = roomService;
    }

    /**
     * Get all rooms for a specified store
     * 
     * @param tenantCode
     *            The tenant code
     * @param storeCode
     *            The store code
     * @return all rooms.
     */
    @GET
    @Path("/")
    public Room[] getRooms(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode) {
	this.injectTenantContext(tenantCode);
	return roomService.getRooms(storeCode).toArray(new Room[0]);
    }
}

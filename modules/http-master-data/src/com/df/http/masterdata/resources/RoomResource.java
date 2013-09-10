package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Room;
import com.df.masterdata.service.inf.RoomServiceInf;

@Path("/{tenantId}/{storeId}/room")
@Produces("application/json")
@Component
public class RoomResource extends TenantResource {

    @Inject
    private RoomServiceInf roomService;

    public void setRoomService(RoomServiceInf roomService) {
	this.roomService = roomService;
    }

    @GET
    @Path("/")
    public List<Room> getRooms(@PathParam("tenantId") String tenantId, @PathParam("storeId") long storeId) {
	return roomService.getRooms(storeId);
    }
}

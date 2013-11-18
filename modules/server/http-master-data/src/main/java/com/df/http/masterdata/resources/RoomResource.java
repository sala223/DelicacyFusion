package com.df.http.masterdata.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

import com.df.core.rs.TenantResource;
import com.df.masterdata.entity.Room;
import com.df.masterdata.service.contract.RoomService;

@Path("/tenant/{tenantCode}/store/{storeCode}/room")
@Produces("application/json;charset=UTF-8")
@Component
public class RoomResource extends TenantResource {

	@Inject
	private RoomService roomService;

	public void setRoomService(RoomService roomService) {
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
	public List<Room> getRooms(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode) {
		this.injectTenantContext(tenantCode);
		return roomService.getRooms(storeCode);
	}

	/**
	 * Add a room to a store.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param room
	 *            The new room to be added.
	 */
	@POST
	@Path("/")
	public void addRoom(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode, Room room) {
		this.injectTenantContext(tenantCode);
		roomService.newRoom(room);
	}

	/**
	 * Update a room.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param room
	 *            The room with new information to be updated.
	 */
	@PUT
	@Path("/")
	public void updateRoom(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        Room room) {
		this.injectTenantContext(tenantCode);
		roomService.updateRoom(storeCode, room);
	}

	/**
	 * Delete a room in a store by the room code.
	 * 
	 * @param tenantCode
	 *            The tenant code
	 * @param storeCode
	 *            The store code
	 * @param roomCode
	 *            The room to be removed.
	 */
	@DELETE
	@Path("/{roomCode}")
	public void removeRoom(@PathParam("tenantCode") String tenantCode, @PathParam("storeCode") String storeCode,
	        @PathParam("roomCode") String roomCode) {
		this.injectTenantContext(tenantCode);
		roomService.removeRoom(storeCode, roomCode);
	}
}

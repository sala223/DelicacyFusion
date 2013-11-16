package com.df.masterdata.dao;

import java.util.List;

import com.df.core.persist.eclipselink.Property;
import com.df.masterdata.entity.Constants.MASTERDATA;
import com.df.masterdata.entity.Constants.ROOM;
import com.df.masterdata.entity.Room;

public class RoomDao extends StoreAwareMasterDataAccessFoundation {

	public Room findRoomByRoomName(String storeCode, String roomName) {
		return findSingleEntityByProperty(Room.class, storeCode, ROOM.NAME_PROPERTY, roomName);
	}

	public Room findRoomByRoomCode(String storeCode, String roomCode) {
		return findSingleEntityByProperty(Room.class, storeCode, MASTERDATA.CODE_PROPERTY, roomCode);
	}

	public List<Room> getRooms(String storeCode) {
		return this.all(storeCode, Room.class);
	}

	public void removeRoomByCode(String storeCode, String roomCode) {
		Property<String> p1 = new Property<String>(ROOM.STORE_CODE_PROPERTY, storeCode);
		Property<String> p2 = new Property<String>(ROOM.CODE_PROPERTY, roomCode);
		this.deleteEntityByProperties(Room.class, p1, p2);
	}
}
package com.df.masterdata.dao;

import java.util.List;

import com.df.masterdata.entity.Constants.MASTERDATA;
import com.df.masterdata.entity.Constants.ROOM;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.entity.Room;
import com.df.masterdata.exception.RoomException;

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

    public List<DiningTable> getRoomDiningTables(String storeCode, String roomCode) {
	Room room = this.findRoomByRoomCode(storeCode, roomCode);
	return room.getDiningTables();
    }

    public void addDiningTable(DiningTable table, String storeCode, String roomCode) {
	Room room = this.findRoomByRoomCode(storeCode, roomCode);
	if (room == null) {
	    throw RoomException.roomWithCodeNotExist(roomCode);
	}
	if (room.getDiningTables().size() >= room.getTableCapacity()) {
	    throw RoomException.exceedTableCapacity(roomCode);
	}

	room.addDiningTable(table);
	this.update(room);
    }
}
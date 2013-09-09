package com.df.masterdata.dal;

import com.df.masterdata.entity.Constants.ROOM;
import com.df.masterdata.entity.Room;

public class RoomDAL extends StoreAwareMasterDataAccessFoundation {

    public Room findRoomByRoomName(long storeId, String roomName) {
	return findSingleEntityByProperty(Room.class, storeId, ROOM.NAME_PROPERTY, roomName);
    }

}
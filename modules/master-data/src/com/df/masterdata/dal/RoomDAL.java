package com.df.masterdata.dal;

import com.df.masterdata.entity.Constants.ROOM;
import com.df.masterdata.entity.Room;

public class RoomDAL extends MasterDataAccessFoundation {

    public Room findRoomByRoomName(String roomName) {
	return findSingleEntityByProperty(Room.class, ROOM.NAME_PROPERTY, roomName);
    }

}
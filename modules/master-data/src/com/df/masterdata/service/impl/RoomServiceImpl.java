package com.df.masterdata.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.RoomDAL;
import com.df.masterdata.entity.Room;
import com.df.masterdata.exception.RoomException;
import com.df.masterdata.service.inf.RoomServiceInf;

@Transactional
public class RoomServiceImpl implements RoomServiceInf {

    @Inject
    private RoomDAL roomDAL;

    @Override
    public void newRoom(long storeId, Room room) {
	Room froom = roomDAL.findRoomByRoomName(storeId, room.getName());
	if (froom != null) {
	    throw RoomException.roomWithNameAlreadyExist(room.getName());
	}
    }

    @Override
    public List<Room> getRooms(long storeId) {
	return roomDAL.all(storeId, Room.class);
    }

    @Override
    public void removeRoom(long roomId) {
	roomDAL.disableMasterData(Room.class, roomId);
    }
}

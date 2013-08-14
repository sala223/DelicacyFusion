package com.df.masterdata.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.RoomDAL;
import com.df.masterdata.entity.Room;
import com.df.masterdata.exception.RoomException;
import com.df.masterdata.service.inf.RoomServiceInf;

@Transactional
public class RoomServiceImpl implements RoomServiceInf {

    private RoomDAL roomDAL;

    @Override
    public int getGuestCapacity(long roomId) {
	return 0;
    }

    @Override
    public void newRoom(Room room) {
	Room froom = roomDAL.findRoomByRoomName(room.getName());
	if (froom != null) {
	    throw RoomException.roomWithNameAlreadyExist(room.getName());
	}
    }
}

package com.df.masterdata.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.RoomDao;
import com.df.masterdata.entity.Room;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.RoomException;
import com.df.masterdata.exception.StoreException;
import com.df.masterdata.service.contract.RoomServiceInf;
import com.df.masterdata.service.contract.StoreServiceInf;

@Transactional
public class RoomServiceImpl implements RoomServiceInf {

    @Inject
    private RoomDao roomDao;

    private StoreServiceInf storeService;

    public void setRoomDao(RoomDao roomDao) {
	this.roomDao = roomDao;
    }

    public void setStoreService(StoreServiceInf storeService) {
	this.storeService = storeService;
    }

    @Override
    public void newRoom(Room room) {
	Store store = storeService.getStoreByCode(room.getStoreCode());
	if (store == null) {
	    throw StoreException.storeWithCodeNotExist(room.getStoreCode());
	}
	Room froom = roomDao.findRoomByRoomName(room.getCode(), room.getName());
	if (froom != null) {
	    throw RoomException.roomWithNameAlreadyExist(room.getName());
	}
	froom = roomDao.findRoomByRoomCode(room.getCode(), room.getCode());
	if (froom != null) {
	    throw RoomException.roomWithCodeNotExist(room.getCode());
	}
	roomDao.insert(room);
    }

    @Override
    public List<Room> getRooms(String storeCode) {
	return roomDao.all(storeCode, Room.class);
    }

    @Override
    public void removeRoom(String storeCode, String roomCode) {
	Room room = roomDao.findRoomByRoomCode(storeCode, roomCode);
	if (room != null) {
	    if (room.getDiningTables().size() > 0) {
		throw RoomException.roomIsNotEmpty(roomCode);
	    } else {
		roomDao.remove(room);
	    }
	}
    }

}

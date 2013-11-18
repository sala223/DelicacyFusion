package com.df.masterdata.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.RoomDao;
import com.df.masterdata.entity.Room;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.RoomException;
import com.df.masterdata.exception.StoreException;
import com.df.masterdata.service.contract.RoomService;
import com.df.masterdata.service.contract.StoreService;

@Transactional
public class RoomServiceImpl implements RoomService {

	@Inject
	private RoomDao roomDao;

	private StoreService storeService;

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	public void setStoreService(StoreService storeService) {
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
		roomDao.removeRoomByCode(storeCode, roomCode);
	}

	@Override
	public void updateRoom(String storeCode, Room room) {
		room.setStoreCode(storeCode);
		roomDao.update(room);
	}

}

package com.df.masterdata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.RoomDao;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.entity.Room;
import com.df.masterdata.exception.RoomException;
import com.df.masterdata.service.contract.DiningTableService;
import com.df.masterdata.service.contract.RoomService;
import com.df.masterdata.service.contract.StoreService;

@Transactional
public class RoomServiceImpl implements RoomService {

	@Inject
	private RoomDao roomDao;

	@Inject
	private StoreService storeService;

	@Inject
	private DiningTableService diningTableService;

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public void setDiningTableService(DiningTableService diningTableService) {
		this.diningTableService = diningTableService;
	}

	@Override
	public void newRoom(String storeCode, Room room) {
		room.setStoreCode(storeCode);
		storeService.checkStore(room.getStoreCode(), true);
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
		Room found = roomDao.findRoomByRoomCode(storeCode, room.getCode());
		found.setChangedTime(new Date());
		found.setDescription(room.getDescription());
		found.setEnabled(room.isEnabled());
		found.setMinimumCost(room.getMinimumCost());
		found.setName(room.getName());
		List<DiningTable> tables = room.getTables();
		ArrayList<String> tableCodes = new ArrayList<String>();
		for (DiningTable table : tables) {
			tableCodes.add(table.getCode());
		}
		List<DiningTable> ts = diningTableService.getDiningTables(storeCode, tableCodes);
		for (DiningTable table : ts) {
			found.addDiningTable(table);
		}
		roomDao.update(found);
	}

}

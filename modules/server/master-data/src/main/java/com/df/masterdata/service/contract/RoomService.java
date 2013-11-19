package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.Room;

public interface RoomService {

	void newRoom(String storeCode,Room room);

	List<Room> getRooms(String storeCode);

	void removeRoom(String storeCode, String roomCode);

	void updateRoom(String storeCode, Room room);
}

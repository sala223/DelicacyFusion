package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.Room;

public interface RoomServiceInf {

    void newRoom(Room room);

    List<Room> getRooms(String storeCode);

    void removeRoom(String storeCode, String roomCode);
}

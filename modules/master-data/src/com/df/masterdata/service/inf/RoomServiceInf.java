package com.df.masterdata.service.inf;

import java.util.List;

import com.df.masterdata.entity.Room;

public interface RoomServiceInf {

    void newRoom(long storeId, Room room);

    List<Room> getRooms(long storeId);

    void removeRoom(long room);
}

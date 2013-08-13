package com.df.masterdata.service.inf;

import com.df.masterdata.entity.Room;

public interface RoomServiceInf {

    int getGuestCapacity(long roomId);

    void newRoom(Room room);
}

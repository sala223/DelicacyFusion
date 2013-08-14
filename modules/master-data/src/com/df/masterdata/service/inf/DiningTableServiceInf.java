package com.df.masterdata.service.inf;

import com.df.masterdata.entity.DiningTable;

public interface DiningTableServiceInf {

    void newDiningTable(DiningTable table);

    void updateDiningTableToRoom(long tableId, long roomId);
}

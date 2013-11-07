package com.df.masterdata.service.contract;

import com.df.masterdata.entity.DiningTable;

public interface DiningTableService {

    void newDiningTable(String storeCode, String roomCode, DiningTable table);

}

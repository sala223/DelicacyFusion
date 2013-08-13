package com.df.masterdata.service.impl;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.DiningTableDAL;
import com.df.masterdata.dal.RoomDAL;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.entity.Room;
import com.df.masterdata.exception.DiningTableException;
import com.df.masterdata.exception.RoomException;
import com.df.masterdata.service.inf.DiningTableServiceInf;

@Transactional
public class DiningTableServiceImpl implements DiningTableServiceInf {

    @Inject
    private DiningTableDAL diningTableDAL;

    @Inject
    private RoomDAL roomDAL;

    @Override
    public void newDiningTable(DiningTable table) {
	DiningTable t = diningTableDAL.findDiningTableByNumber(table.getNumber());
	if (t != null) {
	    throw DiningTableException.tableWithNumberAlreadyExist(table.getNumber());
	}
	if (table.getBarCode() != null) {
	    t = diningTableDAL.findDiningTableByBarCode(table.getBarCode());
	    if (t != null) {
		throw DiningTableException.tableWithBarCodeAlreadyExist(table.getNumber());
	    }
	}
	diningTableDAL.insert(table);
    }

    @Override
    public void updateDiningTableToRoom(long tableId, long roomId) {
	DiningTable table = diningTableDAL.find(DiningTable.class, tableId);
	if (table == null) {
	    throw DiningTableException.tableWithIdNotExist(tableId);
	}
	Room room = roomDAL.find(Room.class, roomId);
	if (room == null) {
	    throw RoomException.roomWithIdNotExist(roomId);
	}
	if(room.getDiningTables().size()>=room.getTableCapacity()){
	 throw RoomException.exceedTableCapacity(roomId);   
	}
	table.setRoom(room);
	diningTableDAL.update(table);
    }

}

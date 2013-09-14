package com.df.masterdata.service.impl;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.DiningTableDao;
import com.df.masterdata.dao.RoomDao;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.exception.DiningTableException;
import com.df.masterdata.service.contract.DiningTableServiceInf;

@Transactional
public class DiningTableServiceImpl implements DiningTableServiceInf {

    @Inject
    private DiningTableDao diningTableDao;

    @Inject
    private RoomDao roomDao;

    public void setDiningTableDao(DiningTableDao diningTableDao) {
	this.diningTableDao = diningTableDao;
    }

    public void setRoomDao(RoomDao roomDao) {
	this.roomDao = roomDao;
    }

    @Override
    public void newDiningTable(String storeCode, String roomCode, DiningTable table) {
	DiningTable t = diningTableDao.findDiningTableByNumber(storeCode, roomCode, table.getNumber());
	if (t != null) {
	    throw DiningTableException.tableWithNumberAlreadyExist(table.getNumber());
	}
	if (table.getBarCode() != null) {
	    t = diningTableDao.findDiningTableByBarCode(table.getBarCode());
	    if (t != null) {
		throw DiningTableException.tableWithBarCodeAlreadyExist(table.getNumber());
	    }
	}
	roomDao.addDiningTable(table, storeCode, roomCode);
    }

}

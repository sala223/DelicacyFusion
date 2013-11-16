package com.df.masterdata.service.impl;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.DiningTableDao;
import com.df.masterdata.dao.RoomDao;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.exception.DiningTableException;
import com.df.masterdata.service.contract.DiningTableService;

@Transactional
public class DiningTableServiceImpl implements DiningTableService {

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
	public void newDiningTable(String storeCode, DiningTable table) {
		DiningTable t = diningTableDao.findDiningTableByCode(storeCode, table.getCode());
		if (t != null) {
			throw DiningTableException.tableWithCodeAlreadyExist(table.getCode());
		}
		if (table.getBarCode() != null) {
			t = diningTableDao.findDiningTableByBarCode(table.getBarCode());
			if (t != null) {
				throw DiningTableException.tableWithBarCodeAlreadyExist(table.getBarCode());
			}
		}
	}

	@Override
	public void removeDiningTableByCode(String storeCode, String code) {
		diningTableDao.removeDiningTableByCode(storeCode, code);
	}

	@Override
	public void removeDiningTableByBarCode(String barCode) {
		diningTableDao.removeDiningTableByBarCode(barCode);
	}

}

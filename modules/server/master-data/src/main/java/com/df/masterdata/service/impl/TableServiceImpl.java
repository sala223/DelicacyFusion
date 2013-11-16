package com.df.masterdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.dao.DiningTableDao;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.service.contract.TableService;

public class TableServiceImpl implements TableService {

	@Autowired
	private DiningTableDao tableDao;

	public void setDiningTableDao(DiningTableDao tableDao) {
		this.tableDao = tableDao;
	}

	@Override
	public List<DiningTable> getTables(String storeCode) {
		return tableDao.all(storeCode, DiningTable.class);
	}

	@Override
	public void addTable(String storeCode, DiningTable table) {
		table.setStoreCode(storeCode);
		tableDao.addDiningTable(table);
	}

	@Override
	public void updateTable(String storeCode, DiningTable table) {
		table.setStoreCode(storeCode);
		table.setEnabled(true);
		tableDao.update(table);
	}

	@Override
	public void removeTableByCode(String storeCode, String code) {
		tableDao.removeDiningTableByCode(storeCode, code);
	}

	@Override
	public void removeTableByBarCode(String barCode) {
		tableDao.removeDiningTableByBarCode(barCode);
	}
}

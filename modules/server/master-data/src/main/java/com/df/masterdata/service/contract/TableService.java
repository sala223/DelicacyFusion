package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.DiningTable;

public interface TableService {

	List<DiningTable> getTables(String storeCode);

	void addTable(String storeCode, DiningTable table);

	void updateTable(String storeCode, DiningTable table);

	void removeTableByCode(String storeCode, String code);

	void removeTableByBarCode(String barCode);
}

package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.DiningTable;

public interface DiningTableService {

	void newDiningTable(String storeCode, DiningTable table);

	List<DiningTable> getDiningTables(String storeCode);

	void removeDiningTableByCode(String storeCode, String code);

	void removeDiningTableByBarCode(String barCode);

}

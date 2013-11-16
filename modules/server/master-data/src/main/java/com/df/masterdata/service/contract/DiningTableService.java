package com.df.masterdata.service.contract;

import com.df.masterdata.entity.DiningTable;

public interface DiningTableService {
	
	void newDiningTable(String storeCode, DiningTable table);

	void removeDiningTableByCode(String storeCode, String code);

	void removeDiningTableByBarCode(String barCode);

}

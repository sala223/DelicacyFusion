package com.df.masterdata.dal;

import com.df.masterdata.entity.Constants.DINING_TABLE;
import com.df.masterdata.entity.DiningTable;

public class DiningTableDAL extends MasterDataAccessFoundation {

    public DiningTable findDiningTableByNumber(String number) {
	return findSingleEntityByProperty(DiningTable.class, DINING_TABLE.NUMBER_PROPERTY, number);
    }

    public DiningTable findDiningTableByBarCode(String barCode) {
	return findSingleEntityByProperty(DiningTable.class, DINING_TABLE.BAR_CODE_PROPERTY, barCode);
    }
}

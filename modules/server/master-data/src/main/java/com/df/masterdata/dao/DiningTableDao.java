package com.df.masterdata.dao;

import org.springframework.util.Assert;

import com.df.core.persist.eclipselink.Property;
import com.df.masterdata.entity.Constants.DINING_TABLE;
import com.df.masterdata.entity.DiningTable;
import com.df.masterdata.exception.DiningTableException;

public class DiningTableDao extends StoreAwareMasterDataAccessFoundation {

	public DiningTable findDiningTableByCode(String storeCode, String code) {
		Property<String> p1 = new Property<String>(DINING_TABLE.STORE_CODE_PROPERTY, storeCode);
		Property<String> p2 = new Property<String>(DINING_TABLE.CODE_PROPERTY, code);
		DiningTable find = findSingleEntityByProperties(DiningTable.class, p1, p2);
		return find;
	}

	public DiningTable findDiningTableByBarCode(String barCode) {
		if (barCode == null || barCode.equals("")) {
			return null;
		}
		return findSingleEntityByProperty(DiningTable.class, DINING_TABLE.BAR_CODE_PROPERTY, barCode);
	}

	public int removeDiningTableByCode(String storeCode, String code) {
		Property<String> p1 = new Property<String>(DINING_TABLE.STORE_CODE_PROPERTY, storeCode);
		Property<String> p2 = new Property<String>(DINING_TABLE.CODE_PROPERTY, code);
		return this.deleteEntityByProperties(DiningTable.class, p1, p2);
	}

	public int removeDiningTableByBarCode(String barCode) {
		Property<String> p1 = new Property<String>(DINING_TABLE.BAR_CODE_PROPERTY, barCode);
		return this.deleteEntityByProperties(DiningTable.class, p1);
	}

	public void addDiningTable(DiningTable table) {
		Assert.notNull(table.getStoreCode());
		DiningTable found = this.findDiningTableByCode(table.getStoreCode(), table.getCode());
		if (found != null) {
			throw DiningTableException.tableWithCodeAlreadyExist(table.getCode());
		}
		found = this.findDiningTableByBarCode(table.getBarCode());
		if (found != null) {
			throw DiningTableException.tableWithBarCodeAlreadyExist(table.getCode());
		}
		this.insert(table);
	}

}

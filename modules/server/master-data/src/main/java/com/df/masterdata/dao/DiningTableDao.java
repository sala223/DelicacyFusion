package com.df.masterdata.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

	public List<DiningTable> getDiningTables(String storeCode, List<String> tableCodes) {
		if (tableCodes == null || tableCodes.size() == 0) {
			return new ArrayList<DiningTable>();
		} else {
			CriteriaBuilder builder = createQueryBuilder();
			CriteriaQuery<DiningTable> query = builder.createQuery(DiningTable.class);
			Root<DiningTable> root = query.from(DiningTable.class);
			Predicate storeEqual = builder.equal(root.get(DINING_TABLE.STORE_CODE_PROPERTY), storeCode);
			Expression<String> code = root.get(DINING_TABLE.CODE_PROPERTY);
			In<Boolean> tableCodeIn = builder.in(code.in(tableCodes));
			query.where(builder.and(storeEqual, tableCodeIn));
			EntityManager em = getEntityManager();
			return em.createQuery(query).getResultList();
		}
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

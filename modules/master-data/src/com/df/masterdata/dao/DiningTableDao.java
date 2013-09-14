package com.df.masterdata.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.df.core.persist.eclipselink.EclipseLinkDataAccessFoundation;
import com.df.masterdata.entity.Constants.DINING_TABLE;
import com.df.masterdata.entity.Constants.ROOM;
import com.df.masterdata.entity.DiningTable;

public class DiningTableDao extends EclipseLinkDataAccessFoundation {

    public DiningTable findDiningTableByNumber(String storeCode, String roomCode, String number) {
	CriteriaBuilder builder = createQueryBuilder();
	CriteriaQuery<DiningTable> query = builder.createQuery(DiningTable.class);
	Root<DiningTable> root = query.from(DiningTable.class);
	Join<Object, Object> join = root.join(DINING_TABLE.ROOM_PROPERTY);
	List<Predicate> predicates = new ArrayList<Predicate>();
	Predicate storeCodeEqual = builder.equal(join.get(ROOM.STORE_CODE_PROPERTY), storeCode);
	predicates.add(storeCodeEqual);
	Predicate roomCodeEqual = builder.equal(join.get(ROOM.CODE_PROPERTY), roomCode);
	predicates.add(roomCodeEqual);
	Predicate isEnabledEqual = builder.equal(join.get(ROOM.IS_ENABLED_PROPERTY), true);
	predicates.add(isEnabledEqual);
	query.where(predicates.toArray(new Predicate[0]));
	return executeSingleQuery(query);
    }

    public DiningTable findDiningTableByBarCode(String barCode) {
	return findSingleEntityByProperty(DiningTable.class, DINING_TABLE.BAR_CODE_PROPERTY, barCode);
    }
}

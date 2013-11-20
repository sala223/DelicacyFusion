package com.df.order.table.occupation;

import java.util.List;

import com.df.order.entity.ServiceCard;

public interface DiningTableDistributor {

	ServiceCard acquireTables(String storeCode, List<String> tables);

	void releaseTables(String storeCode, long serviceCardId);

	List<String> getAvaliableTables(String storeCode);
	
	List<ServiceCard> getTableOccupation(String storeCode);

}

package com.df.client.rs.resource;

import java.util.List;

import com.df.client.rs.model.ServiceCard;

public interface ServiceCardResource {

	public ServiceCard[] getTableOccupation(String storeCode);

	public String[] getAvaliableTables(String storeCode);

	ServiceCard acquireTables(String storeCode, List<String> tables);

	void releaseTables(String storeCode, long serviceCardId);

}

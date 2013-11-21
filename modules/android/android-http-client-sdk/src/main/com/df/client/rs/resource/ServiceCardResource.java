package com.df.client.rs.resource;

import java.util.List;

import com.df.client.rs.model.ServiceCard;

public interface ServiceCardResource extends Resource {

	public ServiceCard[] getTableOccupation();

	public String[] getAvaliableTables();

	ServiceCard acquireTables(List<String> tables);

	void releaseTables(long serviceCardId);

}

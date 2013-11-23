package com.df.client.rs.resource;

import com.df.client.rs.model.DiningTable;

public interface TableResource extends Resource {

	public DiningTable[] getTables();
}

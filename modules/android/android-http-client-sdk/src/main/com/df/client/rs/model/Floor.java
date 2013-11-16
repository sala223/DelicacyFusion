package com.df.client.rs.model;

import java.io.Serializable;
import java.util.List;

public class Floor implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Room> rooms;

	public List<Room> getRooms() {
		return rooms;
	}

	public List<DiningTable> getDiningTables() {
		return null;
	}
}

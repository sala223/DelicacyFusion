package com.df.client.rs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Room extends MasterData {

	private String name;

	private String description;

	private int capacity;

	private double minimumCost;

	private List<DiningTable> diningTables;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCapacity() {
		return capacity;
	}

	public List<DiningTable> getDiningTables() {
		return diningTables;
	}

	public double getMinimumCost() {
		return minimumCost;
	}

	public void setMinimumCost(double minimumCost) {
		this.minimumCost = minimumCost;
	}

	public void addDiningTable(DiningTable table) {
		if (this.diningTables == null) {
			diningTables = new ArrayList<DiningTable>();
		}

		int index = Collections.binarySearch(diningTables, table, new Comparator<DiningTable>() {
			@Override
			public int compare(DiningTable t1, DiningTable t2) {
				return t1.getCode().compareTo(t2.getCode());
			}
		});
		if (index == -1) {
			diningTables.add(table);
			capacity += table.getCapacity();
		}
	}
}

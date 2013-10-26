package com.df.client.rs.model;

import java.util.ArrayList;
import java.util.List;

public class Room extends MasterData {

    private String name;

    private String description;

    private int tableCapacity = Integer.MAX_VALUE;

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

    public int getTableCapacity() {
	return tableCapacity;
    }

    public void setTableCapacity(int tableCapacity) {
	this.tableCapacity = tableCapacity;
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
	if (this.getDiningTables() == null) {
	    diningTables = new ArrayList<DiningTable>();
	}
	diningTables.add(table);
    }
}

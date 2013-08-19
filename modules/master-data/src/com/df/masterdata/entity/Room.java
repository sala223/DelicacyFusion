package com.df.masterdata.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
public class Room extends StoreMasterData {

    @Column(nullable = false)
    @Index(name = "room_name_index", unique = true, columnNames = { "name", MultiTenantSupport.TENANT_COLUMN })
    private String name;

    @Column(length=255)
    private String description;

    @Column
    private int tableCapacity;

    @Column(scale = 2)
    private double minimumCost;

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH })
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
}

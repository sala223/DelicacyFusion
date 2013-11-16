package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

import com.df.core.persist.eclipselink.MultiTenantSupport;
import com.df.masterdata.exception.RoomException;

@XmlRootElement
@Entity
@Indexes({
        @Index(name = "IDX_ROOM_STR_CODE", unique = true, columnNames = { "STORE_CODE", "CODE",
                MultiTenantSupport.TENANT_COLUMN }),
        @Index(name = "IDX_ROOM_STR_NAME", unique = true, columnNames = { "STORE_CODE", "NAME",
                MultiTenantSupport.TENANT_COLUMN }) })
public class Room extends StoreAwareMasterData {

	@Column(nullable = false, name = "NAME")
	private String name;

	@Column(length = 255, name = "DESCRIPTION")
	private String description;

	@Column(name = "TABLE_CAPACITY")
	private int tableCapacity = Integer.MAX_VALUE;

	@Column(scale = 2, name = "MINIMUM_COST")
	private double minimumCost;

	@Column(name = "CURRENCY")
	private String currency;

	@OneToMany
	private List<DiningTable> tables;

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

	public double getMinimumCost() {
		return minimumCost;
	}

	public void setMinimumCost(double minimumCost) {
		this.minimumCost = minimumCost;
	}

	public void addDiningTable(DiningTable table) {
		if (this.tables == null) {
			tables = new ArrayList<DiningTable>();
		}
		if (tables.size() >= this.getTableCapacity()) {
			throw RoomException.exceedTableCapacity(this.getCode());
		} else {
			int index = Collections.binarySearch(tables, table, new Comparator<DiningTable>() {

				@Override
				public int compare(DiningTable t1, DiningTable t2) {
					return t1.getCode().compareTo(t2.getCode());
				}

			});
			if (index == -1) {
				tables.add(table);
			}
		}
	}

}

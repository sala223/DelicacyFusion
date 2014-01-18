package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;
import org.hibernate.validator.constraints.NotEmpty;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@XmlRootElement
@Entity
@Indexes({
        @Index(name = "IDX_ROOM_STR_CODE", unique = true, columnNames = { "STORE_CODE", "CODE",
                MultiTenantSupport.TENANT_COLUMN }),
        @Index(name = "IDX_ROOM_STR_NAME", unique = true, columnNames = { "STORE_CODE", "NAME",
                MultiTenantSupport.TENANT_COLUMN }) })
public class Room extends StoreAwareMasterData {

	@NotEmpty(message = "{room.name.NotEmpty}")
	@Size(message = "{room.name.Size}", max = 255)
	@Column(length = 255, nullable = false, name = "NAME")
	private String name;

	@Size(message = "{room.description.Size}", max = 255)
	@Column(length = 1024, name = "DESCRIPTION")
	private String description;

	@Column(scale = 2, name = "MINIMUM_COST")
	private double minimumCost;

	@Column(name = "CURRENCY")
	private String currency;

	@OneToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "ROOM_TABLE", joinColumns = {
	        @JoinColumn(name = "STORE_CODE", referencedColumnName = "STORE_CODE"),
	        @JoinColumn(name = "ROOM_CODE", referencedColumnName = "CODE") }, inverseJoinColumns = {
	        @JoinColumn(name = "STORE_CODE", referencedColumnName = "STORE_CODE"),
	        @JoinColumn(name = "TABLE_CODE", referencedColumnName = "CODE", unique = true) })
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

	public int getCapacity() {
		int capacity = 0;
		if (tables != null) {
			for (DiningTable table : tables) {
				capacity += table.getCapacity();
			}
		}

		return capacity;
	}

	public double getMinimumCost() {
		return minimumCost;
	}

	public void setMinimumCost(double minimumCost) {
		this.minimumCost = minimumCost;
	}

	public List<DiningTable> getTables() {
		return tables;
	}

	public void addDiningTable(DiningTable table) {
		if (this.tables == null) {
			tables = new ArrayList<DiningTable>();
		}

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

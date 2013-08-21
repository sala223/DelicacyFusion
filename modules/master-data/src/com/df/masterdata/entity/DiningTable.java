package com.df.masterdata.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@Table(name="dining_table")
public class DiningTable extends MasterData {

    @Column(nullable = false, length = 32)
    @Index(name = "room_number_index", unique = true, columnNames = { "name", MultiTenantSupport.TENANT_COLUMN })
    private String number;

    @Column(nullable = false, length = 128)
    @Index(name = "room_barCode_index")
    private String barCode;

    @Column
    private int capacity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Room room;

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getBarCode() {
	return barCode;
    }

    public void setBarCode(String barCode) {
	this.barCode = barCode;
    }

    public int getCapacity() {
	return capacity;
    }

    public void setCapacity(int capacity) {
	this.capacity = capacity;
    }

    public Room getRoom() {
	return room;
    }

    public void setRoom(Room room) {
	this.room = room;
    }

    @Override
    public void fillDefaultValue() {
	super.fillDefaultValue();
	this.capacity = 10;
    }

}

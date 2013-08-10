package com.df.masterdata.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;

import org.eclipse.persistence.annotations.Index;

@Entity
@SecondaryTable(name = "STORE_ADDRESS", pkJoinColumns = @PrimaryKeyJoinColumn(name = "ADDRESS_ID"))
public class Store extends MasterData {

    @Column(nullable = false, length = 256)
    @Index
    private String name;

    @Lob
    @Column
    private String description;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "country", column = @Column(table = "STORE_ADDRESS")),
	    @AttributeOverride(name = "province", column = @Column(table = "STORE_ADDRESS")),
	    @AttributeOverride(name = "city", column = @Column(table = "STORE_ADDRESS")),
	    @AttributeOverride(name = "county", column = @Column(table = "STORE_ADDRESS")),
	    @AttributeOverride(name = "town", column = @Column(table = "STORE_ADDRESS")),
	    @AttributeOverride(name = "address", column = @Column(table = "STORE_ADDRESS")) })
    private Address address;

    @Column(length = 32, nullable = false)
    private String telephone1;

    @Column(length = 32)
    private String telephone2;

    @Column(nullable = false)
    private int businessHourFrom;

    @Column(nullable = false)
    private int businessHourTo;

    @Column(length = 512)
    private String trafficInfo;

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

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public String getTelephone1() {
	return telephone1;
    }

    public void setTelephone1(String telephone1) {
	this.telephone1 = telephone1;
    }

    public String getTelephone2() {
	return telephone2;
    }

    public void setTelephone2(String telephone2) {
	this.telephone2 = telephone2;
    }

    public int getBusinessHourFrom() {
	return businessHourFrom;
    }

    public void setBusinessHourFrom(int businessHourFrom) {
	this.businessHourFrom = businessHourFrom;
    }

    public int getBusinessHourTo() {
	return businessHourTo;
    }

    public void setBusinessHourTo(int businessHourTo) {
	this.businessHourTo = businessHourTo;
    }

    public String getTrafficInfo() {
	return trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
	this.trafficInfo = trafficInfo;
    }
}

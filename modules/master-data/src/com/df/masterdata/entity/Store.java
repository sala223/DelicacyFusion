package com.df.masterdata.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Index;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@Table
@SecondaryTable(name = "store_address", pkJoinColumns = @PrimaryKeyJoinColumn(name = "address_id"))
@Index(name = "store_name_index", unique = true, columnNames = { "name", MultiTenantSupport.TENANT_COLUMN })
public class Store extends MasterData {

    @Column(nullable = false, length = 255)
    private String name;

    @Lob
    @Column
    private String description;

    @Embedded
    @AttributeOverrides({
	    @AttributeOverride(name = "country", column = @Column(name = "COUNTRY", table = "store_address")),
	    @AttributeOverride(name = "province", column = @Column(name = "PROVINCE", table = "store_address")),
	    @AttributeOverride(name = "city", column = @Column(name = "CITY", table = "store_address")),
	    @AttributeOverride(name = "county", column = @Column(name = "COUNTY", table = "store_address")),
	    @AttributeOverride(name = "town", column = @Column(name = "TOWN", table = "store_address")),
	    @AttributeOverride(name = "address", column = @Column(name = "ADDRESS", table = "store_address")) })
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

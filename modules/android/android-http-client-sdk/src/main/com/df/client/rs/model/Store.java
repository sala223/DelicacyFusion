package com.df.client.rs.model;

import java.util.ArrayList;
import java.util.List;

public class Store extends MasterData {

    private String name;

    private String description;

    private List<Address> addresses;

    private String telephone1;

    private String telephone2;

    private int businessHourFrom = -1;

    private int businessHourTo = -1;

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
	if (addresses != null && addresses.size() > 0) {
	    return addresses.get(0);
	}
	return null;
    }

    public void setAddress(Address address) {
	if (addresses == null) {
	    addresses = new ArrayList<Address>();
	}
	this.addresses.add(address);
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

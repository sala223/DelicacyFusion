package com.df.masterdata.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "COUNTRY", nullable = false, length = 128)
    private String country;

    @Column(name = "PROVINCE", nullable = false, length = 128)
    private String province;

    @Column(name = "CITY", nullable = false, length = 128)
    private String city;

    @Column(name = "COUNTY", nullable = false, length = 128)
    private String county;

    @Column(name = "TOWN", nullable = false, length = 128)
    private String town;

    @Column(name = "ADDRESS", nullable = false, length = 512)
    private String address;

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getProvince() {
	return province;
    }

    public void setProvince(String province) {
	this.province = province;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getCounty() {
	return county;
    }

    public void setCounty(String county) {
	this.county = county;
    }

    public String getTown() {
	return town;
    }

    public void setTown(String town) {
	this.town = town;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }
}

package com.df.masterdata.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;

import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
@Index(name = "IDX_STORE_T_NAME", unique = true, columnNames = { "name", MultiTenantSupport.TENANT_COLUMN })
public class Store extends MasterData {

    @Column
    @Index(name = "IDX_STORE_T_CODE", unique = true, columnNames = { "code", MultiTenantSupport.TENANT_COLUMN })
    private String code;

    @Column(nullable = false, length = 255)
    private String name;

    @Lob
    @Column
    private String description;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "STORE_ID"))
    @JoinFetch(JoinFetchType.OUTER)
    private List<Address> addresses;

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

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

}

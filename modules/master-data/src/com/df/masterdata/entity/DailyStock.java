package com.df.masterdata.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.df.core.persist.eclipselink.MultiTenantSupport;

@Entity
public class DailyStock extends MultiTenantSupport {

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Store store;

    public Store getStore() {
	return store;
    }

    public void setStore(Store store) {
	this.store = store;
    }

}

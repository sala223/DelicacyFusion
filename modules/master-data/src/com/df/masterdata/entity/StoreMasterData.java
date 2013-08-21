package com.df.masterdata.entity;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StoreMasterData extends MasterData {

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Store store;

    public Store getStore() {
	return store;
    }

    public void setStore(Store store) {
	this.store = store;
    }

}
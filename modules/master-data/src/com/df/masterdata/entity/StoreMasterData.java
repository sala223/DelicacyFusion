package com.df.masterdata.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StoreMasterData extends MasterData {

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    private Store store;

    public Store getStore() {
	return store;
    }

    /**
     * Set the owner for the master data.
     * 
     * @param store
     *            the owner for the master data, null indicate the master data
     *            is global available.
     */
    public void setStore(Store store) {
	this.store = store;
    }

}

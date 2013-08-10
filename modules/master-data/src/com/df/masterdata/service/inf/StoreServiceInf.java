package com.df.masterdata.service.inf;

import java.util.List;

import com.df.masterdata.entity.Store;

public interface StoreServiceInf {

    void newStore(Store store);

    void disableStore(long storeId);

    List<Store> getStoreList();

    Store getStoreById(long storeId);
}

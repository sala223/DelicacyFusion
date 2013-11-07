package com.df.masterdata.service.contract;

import java.util.List;

import com.df.masterdata.entity.Store;

public interface StoreService {

    void newStore(Store store);

    void disableStore(String storeCode);

    void enableStore(String storeCode);

    List<Store> getStoreList();

    Store getStoreByCode(String storeCode);
}

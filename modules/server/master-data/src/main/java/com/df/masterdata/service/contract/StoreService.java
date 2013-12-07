package com.df.masterdata.service.contract;

import java.io.InputStream;
import java.util.List;

import com.df.masterdata.entity.PictureRef;
import com.df.masterdata.entity.Store;

public interface StoreService {

	void newStore(Store store);

	void updateStore(Store store);

	PictureRef updateStoreImage(String storeCode, InputStream imageStream);

	void deleteStore(String storeCode);

	List<Store> getStoreList();

	Store getStoreByCode(String storeCode);

	Store checkStore(String storeCode, boolean throwException);
}

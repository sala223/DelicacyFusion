package com.df.masterdata.service.contract;

import java.io.InputStream;
import java.util.List;

import com.df.blobstore.image.ImageKey;
import com.df.masterdata.entity.Store;

public interface StoreService {

	void newStore(Store store);

	void updateStore(Store store);

	ImageKey updateStoreImage(String storeCode, InputStream imageStream);

	void deleteStore(String storeCode);

	List<Store> getStoreList();

	Store getStoreByCode(String storeCode);

	Store checkStore(String storeCode, boolean throwException);
}

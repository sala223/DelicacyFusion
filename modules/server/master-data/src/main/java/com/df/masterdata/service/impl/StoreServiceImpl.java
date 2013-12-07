package com.df.masterdata.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.df.blobstore.image.ImageAttributes;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageService;
import com.df.core.common.context.TenantContextHolder;
import com.df.masterdata.dao.StoreDao;
import com.df.masterdata.entity.PictureRef;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.StoreException;
import com.df.masterdata.service.contract.StoreService;

@Transactional(isolation = Isolation.DEFAULT)
public class StoreServiceImpl implements StoreService {

	@Inject
	private StoreDao storeDao;

	@Inject
	private ImageService imageService;

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@Override
	public void newStore(Store store) {
		if (StringUtils.isEmpty(store.getCode())) {
			throw StoreException.valiationError("Store code must not be empty");
		}
		if (StringUtils.isEmpty(store.getName())) {
			throw StoreException.valiationError("Store name must not be empty");
		}
		if (StringUtils.isEmpty(store.getTelephone1())) {
			throw StoreException.valiationError("Store telephone1 must not be empty");
		}
		if (storeDao.getStoreByCode(store.getCode()) != null) {
			throw StoreException.storeWithCodeAlreadyExist(store.getCode());
		}
		if (storeDao.getStoreByName(store.getName()) != null) {
			throw StoreException.storeWithNameAlreadyExist(store.getName());
		} else {
			storeDao.insert(store);
		}
	}

	@Override
	public void updateStore(Store store) {
		Store found = this.checkStore(store.getCode(), true);
		if (!found.getName().equals(store.getName())) {
			if (storeDao.getStoreByName(store.getName()) != null) {
				throw StoreException.storeWithNameAlreadyExist(store.getName());
			}
		}
		found.setAddress(store.getAddress());
		found.setBusinessHourFrom(store.getBusinessHourFrom());
		found.setBusinessHourTo(store.getBusinessHourTo());
		found.setChangedTime(new Date());
		found.setDescription(store.getDescription());
		found.setEnabled(store.isEnabled());
		found.setName(store.getName());
		found.setTelephone1(store.getTelephone1());
		found.setTelephone2(store.getTelephone2());
		found.setTrafficInfo(store.getTrafficInfo());
		found.setImage(store.getImage()); 
		storeDao.update(found);
	}

	@Override
	public List<Store> getStoreList() {
		return storeDao.all(Store.class, 0, Integer.MAX_VALUE, false);
	}

	@Override
	public Store getStoreByCode(String storeCode) {
		return storeDao.getStoreByCode(storeCode);
	}

	public void deleteStore(String storeCode) {
		storeDao.disableMasterData(Store.class, storeCode);
	}

	@Override
	public Store checkStore(String storeCode, boolean throwException) {
		Store found = storeDao.getStoreByCode(storeCode);
		if (found == null && throwException) {
			throw StoreException.storeWithCodeNotExist(storeCode);
		}
		return found;
	}

	@Override
	public PictureRef updateStoreImage(String storeCode, InputStream imageStream) {
		Store store = this.checkStore(storeCode, true);
		String tenantCode = TenantContextHolder.getTenant().getTenantCode();
		ImageKey key = imageService.uploadImage(imageStream, tenantCode);
		ImageAttributes attribute = imageService.getImageKeyResolver().resolveImageAttributes(key);
		PictureRef picture = new PictureRef();
		picture.setImageId(key.getKey());
		picture.setFormat(attribute.getFormat().name());
		picture.setWidth(attribute.getWidth());
		picture.setHeigth(attribute.getHeigth());
		store.setImage(picture);
		this.updateStore(store);
		return picture;
	}
}

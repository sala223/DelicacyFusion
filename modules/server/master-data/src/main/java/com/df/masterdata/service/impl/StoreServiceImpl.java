package com.df.masterdata.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageService;
import com.df.core.common.context.TenantContextHolder;
import com.df.core.validation.exception.ValidationException;
import com.df.masterdata.dao.StoreDao;
import com.df.masterdata.entity.Store;
import com.df.masterdata.exception.StoreException;
import com.df.masterdata.service.contract.StoreService;

@Transactional(isolation = Isolation.DEFAULT)
public class StoreServiceImpl implements StoreService {

	@Inject
	private StoreDao storeDao;

	@Inject
	private ImageService imageService;

	@Inject
	private Validator validator;

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public void newStore(Store store) {
		Set<ConstraintViolation<Store>> violations = validator.validate(store);
		if (!violations.isEmpty()) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
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
		Set<ConstraintViolation<Store>> violations = validator.validate(store);
		if (!violations.isEmpty()) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
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
		found.setImageId(store.getImageId());
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
	public ImageKey updateStoreImage(String storeCode, InputStream imageStream) {
		Store store = this.checkStore(storeCode, true);
		String tenantCode = TenantContextHolder.getTenant().getTenantCode();
		ImageKey key = imageService.uploadImage(imageStream, tenantCode);
		store.setImageId(key.getKey());
		this.updateStore(store);
		return key;
	}
}

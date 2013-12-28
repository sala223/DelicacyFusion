package com.df.masterdata.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.blobstore.image.ImageAttributes;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageService;
import com.df.blobstore.image.ImageStoreException;
import com.df.core.common.context.TenantContextHolder;
import com.df.core.validation.exception.ValidationException;
import com.df.masterdata.dao.ItemTemplateDao;
import com.df.masterdata.entity.ItemTemplate;
import com.df.masterdata.entity.PictureRef;
import com.df.masterdata.exception.ItemTemplateException;
import com.df.masterdata.service.contract.ItemTemplateService;

@Transactional
public class ItemTemplateServiceImpl implements ItemTemplateService {

	@Autowired
	private ItemTemplateDao itemTemplateDao;

	@Autowired
	private ImageService imageService;

	@Autowired
	private Validator validator;

	private static final Logger log = LoggerFactory.getLogger(ItemTemplateServiceImpl.class);

	public void setItemTemplateDao(ItemTemplateDao itemTemplateDao) {
		this.itemTemplateDao = itemTemplateDao;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public ItemTemplate getItemTemplateByCode(String itemCode) {
		return itemTemplateDao.getItemTemplateByCode(itemCode);
	}

	@Override
	public ItemTemplate getItemTemplateByName(String itemName) {
		return itemTemplateDao.getItemTemplateByName(itemName);
	}

	@Override
	public ItemTemplate createItemTemplate(ItemTemplate itemTemplate) {
		Set<ConstraintViolation<ItemTemplate>> violations = validator.validate(itemTemplate);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		itemTemplateDao.newItemTemplate(itemTemplate);
		return itemTemplate;
	}

	@Override
	public void updateItemTemplate(ItemTemplate itemTemplate) {
		Set<ConstraintViolation<ItemTemplate>> violations = validator.validate(itemTemplate);
		if (violations.size() != 0) {
			throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
		}
		ItemTemplate itpl = itemTemplateDao.getItemTemplateByCode(itemTemplate.getCode());
		if (itpl == null) {
			throw ItemTemplateException.itemTemplateWithCodeDoesNotExist(itemTemplate.getCode());
		}
		itpl.setChangedTime(new Date());
		itpl.setCategories(itemTemplate.getCategories().toArray(new String[0]));
		itpl.setCurrency(itemTemplate.getCurrency());
		itpl.setDescription(itemTemplate.getDescription());
		itpl.setEnabled(itemTemplate.isEnabled());
		itpl.setItemUnit(itemTemplate.getItemUnit());
		itpl.setName(itemTemplate.getName());
		itpl.setPrice(itemTemplate.getPrice());
		itpl.setType(itemTemplate.getType());
		List<PictureRef> pictureSet = itemTemplate.getPictureSet();
		itpl.setPictureSet(pictureSet);
		itemTemplateDao.update(itpl);
	}

	@Override
	public PictureRef uploadItemPicture(String itemCode, InputStream stream, String comment) {
		ItemTemplate itemTemplate = itemTemplateDao.getItemTemplateByCode(itemCode);
		ImageKey key = imageService.uploadImage(stream, TenantContextHolder.getTenant().getTenantCode());
		ImageAttributes attribute = imageService.getImageKeyResolver().resolveImageAttributes(key);
		PictureRef picture = new PictureRef();
		picture.setImageId(key.toString());
		picture.setFormat(attribute.getFormat().name());
		picture.setWidth(attribute.getWidth());
		picture.setHeigth(attribute.getHeigth());
		itemTemplate.getPictureSet().add(picture);
		itemTemplateDao.merge(itemTemplate);
		return picture;
	}

	@Override
	public void removeItemPicture(String itemCode, String imageId) {
		ItemTemplate itemTemplate = itemTemplateDao.getItemTemplateByCode(itemCode);
		List<PictureRef> pictureSet = itemTemplate.getPictureSet();
		if (pictureSet == null) {
			return;
		}
		for (PictureRef pic : pictureSet) {
			if (pic.getImageId().equals(imageId)) {
				pictureSet.remove(pic);
			}
		}
		itemTemplateDao.update(itemTemplate);
		try {
			imageService.deleteImage(new ImageKey(imageId));
		} catch (ImageStoreException ex) {
			String msg = "Cannot delete image with image key=" + imageId;
			log.warn(msg, ex);
		}
	}

	@Override
	public List<ItemTemplate> all(boolean includeDisabled) {
		if (includeDisabled) {
			return itemTemplateDao.all(ItemTemplate.class, 0, Integer.MAX_VALUE, true, true);
		} else {
			return itemTemplateDao.all(ItemTemplate.class, 0, Integer.MAX_VALUE, false, true);
		}
	}

	@Override
	public List<ItemTemplate> getItemTemplateByCategory(String categoryCode, boolean includeDisabled) {
		return itemTemplateDao.getItemTemplateByCategory(categoryCode, 0, Integer.MAX_VALUE, includeDisabled);
	}

}

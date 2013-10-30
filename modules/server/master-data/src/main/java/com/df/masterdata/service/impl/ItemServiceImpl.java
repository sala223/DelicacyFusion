package com.df.masterdata.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dao.ItemDao;
import com.df.masterdata.entity.Item;
import com.df.masterdata.exception.ItemException;
import com.df.masterdata.service.contract.ItemServiceInf;

@Transactional
public class ItemServiceImpl implements ItemServiceInf {

    @Inject
    private ItemDao itemDao;

    public void setItemDao(ItemDao itemDao) {
	this.itemDao = itemDao;
    }

    @Override
    public void newItem(Item item) {
	itemDao.newItem(item);
    }

    @Override
    public void disableItem(String storeCode, String itemCode) {
	Item item = itemDao.getItemByItemCode(storeCode, itemCode);
	if (item == null) {
	    throw ItemException.itemWithCodeNotExist(itemCode);
	}

	item.setEnabled(false);
	itemDao.update(item);
    }

    @Override
    public List<Item> getAvaliableItems(String storeCode, int fromResult, int maxResult) {
	return itemDao.all(storeCode, Item.class, fromResult, maxResult, false);
    }

    @Override
    public List<Item> getAvaliableFoods(String storeCode, int fromResult, int maxResult) {
	return itemDao.allFood(storeCode, fromResult, maxResult, false);
    }

    @Override
    public long getAvaliableItemCount(String storeCode) {
	return itemDao.allCount(storeCode, Item.class, false);
    }

    @Override
    public void updateItem(Item item) {
	itemDao.update(item);
    }

    @Override
    public List<Item> getItemsByCategory(String storeCode, String categoryCode) {
	return itemDao.getItemsByCategory(categoryCode, storeCode);
    }

    @Override
    public Map<String, Long> getItemCountGroupByCategory(String storeCode) {
	return itemDao.getItemCountGroupByCategory(storeCode);
    }

    @Override
    public List<Item> getFoodsByCategory(String storeCode, String categoryCode) {
	return itemDao.getFoodsByCategory(categoryCode, storeCode);
    }

    @Override
    public long getAvaliableFoodCount(String storeCode) {
	return itemDao.allFoodCount(storeCode, false);
    }

    @Override
    public void enableItem(String storeCode, String itemCode) {
	Item item = itemDao.getItemByItemCode(storeCode, itemCode);
	if (item == null) {
	    throw ItemException.itemWithCodeNotExist(itemCode);
	}
	item.setEnabled(true);
	itemDao.update(item);
    }

    @Override
    public List<String> listUnavaliableItems(String storeCode,List<String> itemCodes) {
	return itemDao.listUnavaliableItems(storeCode, itemCodes);
    }
}

package com.df.masterdata.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.df.masterdata.dal.ItemDAL;
import com.df.masterdata.entity.Item;
import com.df.masterdata.exception.ItemException;
import com.df.masterdata.service.inf.ItemServiceInf;

@Transactional
public class ItemServiceImpl implements ItemServiceInf {

    @Inject
    private ItemDAL itemDAL;

    @Override
    public void newItem(Item item) {
	itemDAL.insert(item);
    }

    @Override
    public void disableItem(long itemId) {
	Item item = itemDAL.find(Item.class, itemId);
	if (item == null) {
	    throw ItemException.itemWithIdNotExist(itemId);
	}
	item.setEnabled(false);
	itemDAL.update(item);
    }

    @Override
    public List<Item> getItemsByCategory(long categoryId) {
	return itemDAL.getItemsByCategory(categoryId, null);
    }

    @Override
    public List<Item> getAvaliableItems(int fromResult, int maxResult) {
	return itemDAL.all((Long) null, Item.class, fromResult, maxResult, false);
    }

    @Override
    public long getAvaliableItemCount() {
	return itemDAL.allCount(Item.class, false);
    }

    @Override
    public void uploadItemPicture(InputStream stream, String comment) {

    }

    @Override
    public void removeItemPicture(long itemID, String pictureUrl) {
    }

    @Override
    public void updateItem(Item item) {
	itemDAL.update(item);
    }

    @Override
    public List<Item> getItemsByCategory(long storeId, long categoryId) {
	return itemDAL.getItemsByCategory(categoryId, storeId);
    }

    @Override
    public List<Item> getAvaliableItems(long storeId, int fromResult, int maxResult) {
	return itemDAL.all(storeId, Item.class, fromResult, maxResult, false);
    }

    @Override
    public long getAvaliableItemCount(long storeId) {
	return itemDAL.allCount(storeId, Item.class, false);
    }

    @Override
    public Map<Long, Long> getItemCountGroupByCategory(long storeId) {
	return itemDAL.getItemCountGroupByCategory(storeId);
    }

}

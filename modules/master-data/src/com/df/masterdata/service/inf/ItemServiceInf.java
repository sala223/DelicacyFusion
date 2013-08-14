package com.df.masterdata.service.inf;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.df.masterdata.entity.Item;

public interface ItemServiceInf {

    void newItem(Item item);

    void disableItem(long itemId);

    List<Item> getItemsByCategory(long categoryId);

    List<Item> getAvaliableItems(int pageSize, int pageNo);

    long getAvaliableItemCount();

    Map<Long, Long> getItemCountGroupByCategory();

    void uploadItemPicture(InputStream stream, String comment);

    void removeItemPicture(long itemID, String pictureUrl);

    void updateItem(Item item);
}

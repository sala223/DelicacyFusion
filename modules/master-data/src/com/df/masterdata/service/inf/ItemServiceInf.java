package com.df.masterdata.service.inf;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.df.masterdata.entity.Item;

public interface ItemServiceInf {

    void newItem(Item item);

    void disableItem(long itemId);
    
    List<Item> getItemsByCategory(long storeId, long categoryId);

    List<Item> getAvaliableItems(int fromResult, int maxResult);

    List<Item> getAvaliableItems(long storeId, int fromResult, int maxResult);

    long getAvaliableItemCount();

    long getAvaliableItemCount(long storeId);

    Map<Long, Long> getItemCountGroupByCategory(long storeId);

    void uploadItemPicture(InputStream stream, String comment);

    void removeItemPicture(long itemID, String pictureUrl);

    void updateItem(Item item);
}

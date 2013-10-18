package com.df.masterdata.service.contract;

import java.util.List;
import java.util.Map;

import com.df.masterdata.entity.Item;

public interface ItemServiceInf {

    void newItem(Item item);

    void disableItem(String storeCode, String itemCode);

    void enableItem(String storeCode, String itemCode);

    List<Item> listAvaliableItems(String storeCode, List<String> itemCodes);

    List<Item> getItemsByCategory(String storeCode, String categoryCode);

    List<Item> getFoodsByCategory(String storeCode, String categoryCode);

    List<Item> getAvaliableItems(String storeCode, int fromResult, int maxResult);

    List<Item> getAvaliableFoods(String storeCode, int fromResult, int maxResult);

    long getAvaliableItemCount(String storeCode);

    long getAvaliableFoodCount(String storeCode);

    Map<String, Long> getItemCountGroupByCategory(String storeCode);

    void updateItem(Item item);
}

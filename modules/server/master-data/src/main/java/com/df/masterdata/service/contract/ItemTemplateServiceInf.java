package com.df.masterdata.service.contract;

import java.io.InputStream;
import java.util.List;

import com.df.masterdata.entity.ItemTemplate;

public interface ItemTemplateServiceInf {

    ItemTemplate getItemTemplateByCode(String itemCode);

    ItemTemplate getItemTemplateByName(String itemName);

    void uploadItemPicture(String itemCode, InputStream stream, String comment);

    void removeItemPicture(String itemCode, String imageId);

    List<ItemTemplate> getItemTemplateByCategory(String categoryCode, boolean includeDisabled);
}

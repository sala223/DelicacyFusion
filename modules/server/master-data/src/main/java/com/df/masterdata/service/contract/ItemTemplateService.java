package com.df.masterdata.service.contract;

import java.io.InputStream;
import java.util.List;

import com.df.blobstore.image.ImageKey;
import com.df.masterdata.entity.ItemTemplate;

public interface ItemTemplateService {

    ItemTemplate getItemTemplateByCode(String itemCode);

    ItemTemplate getItemTemplateByName(String itemName);

    ItemTemplate createItemTemplate(ItemTemplate itemTemplate);

    void updateItemTemplate(ItemTemplate itemTemplate);

    ImageKey uploadItemPicture(String itemCode, InputStream stream, String comment);

    void removeItemPicture(String itemCode, String imageId);
    
    List<ItemTemplate> all(boolean includeDisabled);

    List<ItemTemplate> getItemTemplateByCategory(String categoryCode, boolean includeDisabled);
 
}

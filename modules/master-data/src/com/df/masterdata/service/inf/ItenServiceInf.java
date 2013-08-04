package com.df.masterdata.service.inf;

import java.io.InputStream;

import com.df.core.image.ImageLocator;
import com.df.masterdata.entity.Item;

public interface ItenServiceInf {

    Item newItem(Item item);

    int updateItem(Item item);

    ImageLocator uploadImageForFood(long itemId, InputStream imageStream);
}

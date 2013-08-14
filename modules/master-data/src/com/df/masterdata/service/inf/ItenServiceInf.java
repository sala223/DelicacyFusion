package com.df.masterdata.service.inf;

import java.io.InputStream;

import com.df.core.image.ImageId;
import com.df.masterdata.entity.Item;

public interface ItenServiceInf {

    Item newItem(Item item);

    int updateItem(Item item);

    ImageId uploadImageForFood(long itemId, InputStream imageStream);
}

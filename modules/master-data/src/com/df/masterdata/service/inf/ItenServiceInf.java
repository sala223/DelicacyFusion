package com.df.masterdata.service.inf;

import java.io.InputStream;

import com.df.blobstore.image.ImageKey;
import com.df.masterdata.entity.Item;

public interface ItenServiceInf {

    Item newItem(Item item);

    int updateItem(Item item);

    ImageKey uploadImageForFood(long itemId, InputStream imageStream);
}

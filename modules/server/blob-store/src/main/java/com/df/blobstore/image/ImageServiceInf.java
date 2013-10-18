package com.df.blobstore.image;

import java.io.InputStream;

public interface ImageServiceInf {

    public abstract ImageKey uploadImage(InputStream in, String tenantId);

    public abstract void deleteImage(ImageKey imageKey);

    public abstract Image fetchImage(ImageKey imageKey);

}
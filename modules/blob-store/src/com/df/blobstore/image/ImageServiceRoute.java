package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleService;

public interface ImageServiceRoute {

    BundleService getBundleService(ImageKey imageKey);

    ImageMetadata resolveImageMetadata(ImageKey imageKey);

}

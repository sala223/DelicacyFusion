package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleService;

public interface ImageServiceRoute extends ImageKeyResolver {

    BundleService getBundleService(ImageKey imageKey);

    ImageMetadata resolveImageMetadata(ImageKey imageKey);

    ImageKey hash(ImageMetadata imageMetadata);

}

package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleService;

public interface ImageServiceRoute extends ImageKeyResolver {

    BundleService getBundleService(ImageKey imageKey);

    ImageAttributes resolveImageAttributes(ImageKey imageKey);

    ImageKey hash(ImageAttributes imageMetadata);

}

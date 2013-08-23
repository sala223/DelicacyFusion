package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleKey;

public interface ImageKeyResolver {

    ImageMetadata resolveImageMetadata(ImageKey imageKey);

    ImageKey hash(ImageMetadata imageMetadata);

    BundleKey resolveBundleKey(ImageKey imageKey);
}

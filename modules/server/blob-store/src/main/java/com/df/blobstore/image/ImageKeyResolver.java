package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleKey;

public interface ImageKeyResolver {

    ImageAttributes resolveImageAttributes(ImageKey imageKey);

    ImageKey hash(ImageAttributes attributes);

    BundleKey resolveBundleKey(ImageKey imageKey);
}

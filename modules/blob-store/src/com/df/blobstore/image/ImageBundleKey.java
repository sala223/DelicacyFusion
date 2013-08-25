package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleKey;

public class ImageBundleKey implements BundleKey {

    private ImageAttributes metadata;

    public ImageBundleKey(ImageAttributes metadata) {
	this.metadata = metadata;
    }

    @Override
    public String getKeyInBundle() {
	String key = metadata.getUniqueId();
	key += "_" + metadata.getWidth() + "_" + metadata.getHeigth();
	key += "." + metadata.getFormat().name().toLowerCase();
	return key;
    }

    @Override
    public String toString() {
	return getKeyInBundle();
    }
}

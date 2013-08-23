package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleKey;

public class ImageBundleKey implements BundleKey {

    private ImageMetadata metadata;

    public ImageBundleKey(ImageMetadata metadata) {
	this.metadata = metadata;
    }

    @Override
    public String getKeyInBundle() {
	String key = metadata.getRandomValue();
	key += metadata.getWidth() + "_" + metadata.getHeigth();
	key += "." + metadata.getFormat().name().toLowerCase();
	return key;
    }

}

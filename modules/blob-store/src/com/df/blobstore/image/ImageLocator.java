package com.df.blobstore.image;

import org.apache.hadoop.fs.Path;

import com.df.blobstore.bundle.BundleKey;

public abstract class ImageLocator {

    private ImageKey imageKey;

    public ImageLocator(ImageKey imageKey) {
	this.imageKey = imageKey;
    }

    protected ImageKey getImageKey() {
	return imageKey;
    }

    public abstract Path getBundlePath();

    public abstract BundleKey getKeyInBundle();
}

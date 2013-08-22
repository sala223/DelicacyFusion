package com.df.blobstore.image;

import com.df.blobstore.bundle.BundleService;

public class FileSystemImageServiceRoute implements ImageServiceRoute {

    @Override
    public BundleService getBundleService(ImageKey imageKey) {
	return null;
    }

    @Override
    public ImageMetadata resolveImageMetadata(ImageKey imageKey) {
	return null;
    }

}

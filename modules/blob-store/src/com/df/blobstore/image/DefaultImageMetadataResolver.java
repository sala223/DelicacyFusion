package com.df.blobstore.image;

public class DefaultImageMetadataResolver implements ImageMetadataResolver {

    @Override
    public ImageMetadata resolveImageMetadata(ImageKey imageKey) {
	String key = imageKey.getKey();
	return null;
    }

    @Override
    public ImageKey hash(ImageMetadata imageMetadata) {
	return null;
    }

}

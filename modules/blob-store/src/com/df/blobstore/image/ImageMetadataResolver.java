package com.df.blobstore.image;

public interface ImageMetadataResolver {

    ImageMetadata resolveImageMetadata(ImageKey imageKey);

    ImageKey hash(ImageMetadata imageMetadata);
}

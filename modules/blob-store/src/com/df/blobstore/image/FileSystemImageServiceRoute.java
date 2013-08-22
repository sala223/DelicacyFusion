package com.df.blobstore.image;

import java.io.File;

import com.df.blobstore.bundle.BundleService;
import com.df.blobstore.bundle.FileSystemBundleService;

public class FileSystemImageServiceRoute implements ImageServiceRoute {

    private ImageMetadataResolver metadataResolver;

    private File imageRootDirectory;

    public FileSystemImageServiceRoute() {
    }

    public FileSystemImageServiceRoute(File imageRootDirectory, ImageMetadataResolver metadataResolver) {
	this.metadataResolver = metadataResolver;
    }

    public void setMetadataResolver(ImageMetadataResolver metadataResolver) {
	this.metadataResolver = metadataResolver;
    }

    public void setImageRootDirectory(File imageRootDirectory) {
	this.imageRootDirectory = imageRootDirectory;
    }

    @Override
    public BundleService getBundleService(ImageKey imageKey) {
	return new FileSystemBundleService(imageRootDirectory);
    }

    @Override
    public ImageMetadata resolveImageMetadata(ImageKey imageKey) {
	return metadataResolver.resolveImageMetadata(imageKey);
    }

    @Override
    public ImageKey hash(ImageMetadata imageMetadata) {
	return metadataResolver.hash(imageMetadata);
    }

}

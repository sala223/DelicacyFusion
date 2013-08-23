package com.df.blobstore.image;

import java.io.File;

import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.BundleService;
import com.df.blobstore.bundle.FileSystemBundleService;

public class FileSystemImageServiceRoute implements ImageServiceRoute {

    private ImageKeyResolver keyResolver;

    private File imageRootDirectory;

    public FileSystemImageServiceRoute() {
    }

    public FileSystemImageServiceRoute(File imageRootDirectory, ImageKeyResolver keyResolver) {
	this.keyResolver = keyResolver;
    }

    public void setMetadataResolver(ImageKeyResolver keyResolver) {
	this.keyResolver = keyResolver;
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
	return keyResolver.resolveImageMetadata(imageKey);
    }

    @Override
    public ImageKey hash(ImageMetadata imageMetadata) {
	return keyResolver.hash(imageMetadata);
    }

    @Override
    public BundleKey resolveBundleKey(ImageKey imageKey) {
	return keyResolver.resolveBundleKey(imageKey);
    }

}

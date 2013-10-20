package com.df.blobstore.image;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.BundleService;
import com.df.blobstore.bundle.FileSystemBundleService;

public class FileSystemImageServiceRoute implements ImageServiceRoute {

    private ImageKeyResolver keyResolver;

    private File imageRootDirectory;

    public FileSystemImageServiceRoute(File imageRootDirectory, ImageKeyResolver keyResolver) {
	this.keyResolver = keyResolver;
	this.imageRootDirectory = imageRootDirectory;
    }

    public FileSystemImageServiceRoute(Path path, ImageKeyResolver keyResolver) {
	this(path.toFile(), keyResolver);
    }

    public FileSystemImageServiceRoute(String path, ImageKeyResolver keyResolver) {
	this(new File(path), keyResolver);
    }

    public FileSystemImageServiceRoute(ImageKeyResolver keyResolver) {
	this(Paths.get(System.getProperty("user.home")), keyResolver);
    }

    public void setMetadataResolver(ImageKeyResolver keyResolver) {
	this.keyResolver = keyResolver;
    }

    public void setImageRootDirectory(File imageRootDirectory) {
	this.imageRootDirectory = imageRootDirectory;
    }

    @Override
    public BundleService getBundleService(ImageKey imageKey) {
	ImageAttributes metadata = this.resolveImageAttributes(imageKey);
	String owner = metadata.getOwner();
	if (owner == null) {
	    throw new ImageStoreException("must specify owner attribute in image key %s", imageKey);
	}
	File dir = new File(imageRootDirectory, owner);
	if (!dir.exists()) {
	    dir.mkdirs();
	}
	return new FileSystemBundleService(new File(imageRootDirectory, owner));
    }

    @Override
    public ImageAttributes resolveImageAttributes(ImageKey imageKey) {
	return keyResolver.resolveImageAttributes(imageKey);
    }

    @Override
    public ImageKey hash(ImageAttributes imageMetadata) {
	return keyResolver.hash(imageMetadata);
    }

    @Override
    public BundleKey resolveBundleKey(ImageKey imageKey) {
	return keyResolver.resolveBundleKey(imageKey);
    }

}

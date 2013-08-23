package com.df.blobstore.image;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.BundleService;

public class BufferedImageServiceRoute implements ImageServiceRoute {

    private ImageServiceRoute serviceRoute;

    private ConcurrentHashMap<String, SoftReference<BundleService>> cache;

    public BufferedImageServiceRoute(ImageServiceRoute serviceRoute) {
	this.serviceRoute = serviceRoute;
	this.cache = new ConcurrentHashMap<String, SoftReference<BundleService>>();
    }

    @Override
    public BundleService getBundleService(ImageKey imageKey) {
	SoftReference<BundleService> reference = this.cache.get(imageKey.getKey());
	if (reference != null) {
	    BundleService bundleService = reference.get();
	    if (bundleService != null) {
		return bundleService;
	    }
	}
	BundleService bundleService = serviceRoute.getBundleService(imageKey);
	if (bundleService != null) {
	    cache.putIfAbsent(imageKey.getKey(), new SoftReference<BundleService>(bundleService));
	}
	return bundleService;
    }

    @Override
    public ImageMetadata resolveImageMetadata(ImageKey imageKey) {
	return serviceRoute.resolveImageMetadata(imageKey);
    }

    public void clearCache() {
	cache.clear();
    }

    public void evict(ImageKey imageKey) {
	if (imageKey != null && imageKey.getKey() != null) {
	    cache.remove(imageKey.getKey());
	}
    }

    @Override
    public ImageKey hash(ImageMetadata imageMetadata) {
	return serviceRoute.hash(imageMetadata);
    }

    @Override
    public BundleKey resolveBundleKey(ImageKey imageKey) {
	return serviceRoute.resolveBundleKey(imageKey);
    }
}

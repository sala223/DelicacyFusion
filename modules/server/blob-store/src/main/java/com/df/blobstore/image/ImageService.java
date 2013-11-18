package com.df.blobstore.image;

import java.io.InputStream;

import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.BundleService;

public class ImageService implements ImageServiceInf {

	private ImageServiceRoute serviceRoute;

	public ImageService(ImageServiceRoute serviceRoute) {
		this.serviceRoute = serviceRoute;
	}

	public ImageKeyResolver getImageKeyResolver() {
		return serviceRoute;
	}

	@Override
	public ImageKey uploadImage(InputStream in, String tenantId) {
		Image image = ImageFactory.createImageFromStream(in, tenantId);
		ImageKey imageKey = serviceRoute.hash(image.getImageAttributes());
		BundleService bundleService = serviceRoute.getBundleService(imageKey);
		bundleService.addBlob(image);
		return imageKey;
	}

	@Override
	public void deleteImage(ImageKey imageKey) {
		BundleService bundleService = serviceRoute.getBundleService(imageKey);
		BundleKey bundleKey = serviceRoute.resolveBundleKey(imageKey);
		bundleService.deleteBlob(bundleKey);
	}

	@Override
	public Image fetchImage(ImageKey imageKey) {
		Image image = ImageFactory.createBlankImage(serviceRoute, imageKey);
		BundleService bundleService = serviceRoute.getBundleService(imageKey);
		boolean succeed = bundleService.fetchBlob(image, serviceRoute.resolveBundleKey(imageKey));
		return succeed ? image : null;
	}
}

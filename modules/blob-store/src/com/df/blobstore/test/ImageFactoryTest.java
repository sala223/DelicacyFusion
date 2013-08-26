package com.df.blobstore.test;

import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.blobstore.bundle.BundleService;
import com.df.blobstore.image.DefaultImageKeyResolver;
import com.df.blobstore.image.FileSystemImageServiceRoute;
import com.df.blobstore.image.Image;
import com.df.blobstore.image.ImageFactory;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageKeyResolver;

public class ImageFactoryTest {

    @Test
    public void testCreateImageFromStream() {
	String testImage = "com/df/blobstore/test/bsd.jpg";
	InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(testImage);
	Image image = ImageFactory.createImageFromStream(in, "test");
	ImageKeyResolver keyResolver = new DefaultImageKeyResolver();
	ImageKey imageKey = keyResolver.hash(image.getImageAttributes());
	FileSystemImageServiceRoute serviceRoute = new FileSystemImageServiceRoute(keyResolver);
	BundleService bundleService = serviceRoute.getBundleService(imageKey);
	bundleService.addBlob(image);
	image = ImageFactory.createBlankImage(keyResolver, imageKey);
	bundleService.fetchBlob(image, serviceRoute.resolveBundleKey(imageKey));
	TestCase.assertTrue(image.getBundleValue().getSize() > 1);
    }

}

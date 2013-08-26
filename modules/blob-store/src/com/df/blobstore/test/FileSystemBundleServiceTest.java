package com.df.blobstore.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.FileSystemBundleService;
import com.df.blobstore.image.DefaultImageKeyResolver;
import com.df.blobstore.image.FileSystemImageServiceRoute;
import com.df.blobstore.image.Image;
import com.df.blobstore.image.ImageFactory;
import com.df.blobstore.image.ImageFormat;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageAttributes;

public class FileSystemBundleServiceTest {

    @Test
    public void testSaveAndDeleteImage() {
	ImageAttributes attribute = new ImageAttributes(12, 24, ImageFormat.GIF);
	attribute.setOwner("test");
	DefaultImageKeyResolver keyResolver = new DefaultImageKeyResolver();
	FileSystemImageServiceRoute serviceRoute = new FileSystemImageServiceRoute(keyResolver);
	ImageKey imageKey = serviceRoute.hash(attribute);
	FileSystemBundleService bundleService = (FileSystemBundleService) serviceRoute.getBundleService(imageKey);
	Image image = new Image(attribute, new byte[] { 12, 12 });
	bundleService.addBlob(image);
	BundleKey bundleKey = image.getBundleKey();
	Image found = ImageFactory.createBlankImage(keyResolver, imageKey);
	bundleService.fetchBlob(found, bundleKey);
	TestCase.assertTrue(found.getBundleValue().getSize() > 0);
	bundleService.deleteBlob(bundleKey);
	TestCase.assertFalse(bundleService.fetchBlob(found, bundleKey));
    }
}

package com.df.blobstore.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.FileSystemBundleService;
import com.df.blobstore.image.DefaultImageKeyResolver;
import com.df.blobstore.image.FileSystemImageServiceRoute;
import com.df.blobstore.image.Image;
import com.df.blobstore.image.ImageFormat;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageAttributes;

public class FileSystemBundleServiceTest {

    @Test
    public void testSaveAndDeleteImage() {
	ImageAttributes metadata = new ImageAttributes(12, 24, ImageFormat.GIF);
	metadata.setBitDepth(24);
	metadata.setOwner("test");
	DefaultImageKeyResolver keyResolver = new DefaultImageKeyResolver();
	FileSystemImageServiceRoute serviceRoute = new FileSystemImageServiceRoute(keyResolver);
	ImageKey imageKey = serviceRoute.hash(metadata);
	FileSystemBundleService bundleService = (FileSystemBundleService) serviceRoute.getBundleService(imageKey);
	Image image = new Image(metadata, new byte[] { 12, 12 });
	bundleService.addBlob(image);
	BundleKey bundleKey = image.getBundleKey();
	Image found = new Image(metadata);
	bundleService.fetchBlob(found, bundleKey);
	TestCase.assertNotNull(found.getImageMetadata());
	TestCase.assertEquals(found.getImageMetadata().getWidth(), 12);
    }
}

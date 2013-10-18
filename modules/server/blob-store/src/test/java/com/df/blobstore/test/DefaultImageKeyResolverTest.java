package com.df.blobstore.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.image.DefaultImageKeyResolver;
import com.df.blobstore.image.ImageFormat;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageKeyResolver;
import com.df.blobstore.image.ImageAttributes;
import com.df.blobstore.image.ImageStoreException;

public class DefaultImageKeyResolverTest {

    @Test
    public void testHashImageKey() {
	ImageKeyResolver keyResolver = new DefaultImageKeyResolver();
	ImageAttributes metadata = new ImageAttributes(12, 24, ImageFormat.GIF);
	metadata.setOwner("test");
	String uniqueId = metadata.getUniqueId();
	ImageKey key = keyResolver.hash(metadata);
	System.out.println("Hash key is:" + key.getKey());
	metadata = keyResolver.resolveImageAttributes(key);
	TestCase.assertEquals(12, metadata.getWidth());
	TestCase.assertEquals(24, metadata.getHeigth());
	TestCase.assertEquals(ImageFormat.GIF, metadata.getFormat());
	TestCase.assertEquals("test", metadata.getOwner());
	TestCase.assertEquals(uniqueId, metadata.getUniqueId());
    }

    @Test
    public void testHashImageKeyWihoutOwnerAttrOrUniqueIdAttr() {
	ImageKeyResolver keyResolver = new DefaultImageKeyResolver();
	ImageAttributes metadata = new ImageAttributes(12, 24, ImageFormat.GIF);
	metadata.setUniqueId("334343");
	try {
	    keyResolver.hash(metadata);
	    TestCase.fail();
	} catch (ImageStoreException ex) {
	}

	metadata.setUniqueId(null);
	metadata.setOwner("test");
	try {
	    keyResolver.hash(metadata);
	    TestCase.fail();
	} catch (ImageStoreException ex) {
	}

    }

    @Test
    public void testResolveBundleKey() {
	ImageKeyResolver keyResolver = new DefaultImageKeyResolver();
	ImageAttributes metadata = new ImageAttributes(12, 24, ImageFormat.GIF);
	metadata.setOwner("test");
	ImageKey key = keyResolver.hash(metadata);
	BundleKey bundleKey = keyResolver.resolveBundleKey(key);
	System.out.println(bundleKey.toString());
    }
}

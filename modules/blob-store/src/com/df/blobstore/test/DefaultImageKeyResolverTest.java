package com.df.blobstore.test;

import org.junit.Test;

import com.df.blobstore.image.DefaultImageKeyResolver;
import com.df.blobstore.image.ImageFormat;
import com.df.blobstore.image.ImageKey;
import com.df.blobstore.image.ImageKeyResolver;
import com.df.blobstore.image.ImageMetadata;

public class DefaultImageKeyResolverTest {

    @Test
    public void testHashImageKey() {
	ImageKeyResolver keyResolver = new DefaultImageKeyResolver();
	ImageMetadata metadata = new ImageMetadata(12, 24, ImageFormat.GIF);
	metadata.setBitDepth(24);
	metadata.setOwner("test");
	ImageKey key = keyResolver.hash(metadata);
	System.out.println(key.getKey());
    }

}

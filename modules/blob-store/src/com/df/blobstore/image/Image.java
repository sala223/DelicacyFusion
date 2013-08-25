package com.df.blobstore.image;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.df.blobstore.bundle.Blob;
import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.BundleValue;

public class Image implements Blob {

    public static final int RGB2GRAY = 0x01;

    private ImageAttributes imageAttributes;

    private byte[] rawData;

    public Image(ImageAttributes imageMetadata) {
	this.imageAttributes = imageMetadata;
    }

    public Image(ImageAttributes imageMetadata, byte[] rawData) {
	this.imageAttributes = imageMetadata;
	this.rawData = rawData;
    }

    @Override
    public BundleKey getBundleKey() {
	return new ImageBundleKey(imageAttributes);
    }

    @Override
    public BundleValue getBundleValue() {
	return new ImageBundleValue();
    }

    public ImageAttributes getImageMetadata() {
	return imageAttributes;
    }

    class ImageBundleValue implements BundleValue {
	@Override
	public InputStream getDataInBundle() {
	    return new DataInputStream(new ByteArrayInputStream(rawData));
	}

	@Override
	public int getSize() {
	    return rawData.length;
	}
    }

    @Override
    public void readBundleValue(InputStream input) throws IOException {
	this.rawData = new byte[input.available()];
	input.read(rawData);
    }
}
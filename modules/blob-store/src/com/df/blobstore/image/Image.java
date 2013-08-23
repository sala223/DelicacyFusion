package com.df.blobstore.image;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.df.blobstore.bundle.Blob;
import com.df.blobstore.bundle.BundleKey;
import com.df.blobstore.bundle.BundleValue;
import com.df.core.common.utils.ByteUtils;

public class Image implements Blob {

    public static final int RGB2GRAY = 0x01;

    private ImageMetadata imageMetadata;

    private float[] pels;

    public Image(ImageMetadata imageMetadata, float[] pels) {
	this.imageMetadata = imageMetadata;
	this.pels = pels;
    }

    Image(ImageMetadata imageMetadata) {
	this.imageMetadata = imageMetadata;
	this.pels = new float[imageMetadata.getWidth() * imageMetadata.getHeigth() * imageMetadata.getBitDepth()];
    }

    @Override
    public BundleKey getBundleKey() {
	return new ImageBundleKey(imageMetadata);
    }

    @Override
    public BundleValue getBundleValue() {
	return new ImageBundleValue();
    }

    public ImageMetadata getImageMetadata() {
	return imageMetadata;
    }

    class ImageBundleValue implements BundleValue {
	@Override
	public InputStream getDataInBundle() {
	    return new DataInputStream(new ByteArrayInputStream(ByteUtils.FloatArraytoByteArray(pels)));
	}

	@Override
	public int getSize() {
	    return pels.length * 4;
	}
    }

    @Override
    public void readBundleKey(String key) throws IOException {
	// TODO Auto-generated method stub

    }

    @Override
    public void readBundleValue(InputStream input) throws IOException {
	// TODO Auto-generated method stub

    }
}
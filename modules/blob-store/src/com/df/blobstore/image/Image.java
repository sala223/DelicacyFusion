package com.df.blobstore.image;

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
	return new ImageBundleKey();
    }

    @Override
    public BundleValue getBundleValue() {
	return new ImageBundleValue();
    }

    public ImageMetadata getImageMetadata() {
	return imageMetadata;
    }

    class ImageBundleKey implements BundleKey {

	@Override
	public String getKeyInBundle() {
	    String key = imageMetadata.getRandomValue();
	    key += imageMetadata.getWidth() + "_" + imageMetadata.getHeigth();
	    key += "." + imageMetadata.getFormat().shortName();
	    return key;
	}

    }

    class ImageBundleValue implements BundleValue {
	@Override
	public byte[] getDataInBundle() {
	    return ByteUtils.FloatArraytoByteArray(pels);
	}

	@Override
	public int getSize() {
	    return pels.length * 4;
	}
    }
}
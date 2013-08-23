package com.df.blobstore.image;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.df.blobstore.bundle.BundleKey;

public class DefaultImageKeyResolver implements ImageKeyResolver {

    private static Charset charset = Charset.forName("utf-8");

    @Override
    public ImageMetadata resolveImageMetadata(ImageKey imageKey) {
	byte[] bytes = imageKey.getKey().getBytes(charset);
	DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
	try {
	    int width = in.readInt();
	    int heigth = in.readInt();
	    int bitDepth = in.readInt();
	    int formatIntValue = in.readInt();
	    ImageFormat format = ImageFormat.fromIntValue(formatIntValue);
	    if (format == null) {
		throw new ImageStoreException("unknow or invalid image format:%s", formatIntValue);
	    }
	    ImageMetadata metadata = new ImageMetadata(width, heigth, format);
	    metadata.setBitDepth(bitDepth);
	    int nextLength = in.readInt();
	    if (nextLength == 0) {
		throw new ImageStoreException("image owner is not specified in the key");
	    }
	    byte[] owner = new byte[nextLength];
	    in.readFully(owner);
	    metadata.setOwner(new String(owner, charset));
	    nextLength = in.readInt();
	    if (nextLength == 0) {
		return metadata;
	    }
	    while (nextLength > 0) {
		byte[] key = new byte[nextLength];
		in.readFully(key);
		nextLength = in.readInt();
		if (nextLength == 0) {
		    byte[] value = new byte[nextLength];
		    in.readFully(value);
		    metadata.addExtraInformation(new String(key, charset), new String(value, charset));
		} else {
		    metadata.addExtraInformation(new String(key, charset), null);
		}
		nextLength = in.readInt();
	    }
	    return metadata;
	} catch (IOException ex) {
	    throw new ImageStoreException("Invalid image key, cannot resolve image metadata.");
	}
    }

    @Override
    public ImageKey hash(ImageMetadata metadata) {
	int bufferSize = 512;
	ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
	int length = 16;
	buffer.putInt(metadata.getWidth());
	buffer.putInt(metadata.getHeigth());
	buffer.putInt(metadata.getBitDepth());
	buffer.putInt(metadata.getFormat().intValue());
	byte[] owner = metadata.getOwner().getBytes(charset);
	length += 4 + owner.length + 4;
	if (length > bufferSize) {
	    throw new ImageStoreException("owner information is too long.");
	}
	buffer.putInt(owner.length);
	buffer.put(owner);
	buffer.putInt(0); // Indicate the end of the key.
	return new ImageKey(new String(buffer.array(), charset));
    }

    @Override
    public BundleKey resolveBundleKey(ImageKey imageKey) {
	ImageMetadata metadata = resolveImageMetadata(imageKey);
	return new SimpleBundleKey(metadata);
    }

    static class SimpleBundleKey implements BundleKey {

	private ImageMetadata metadata;

	public SimpleBundleKey(ImageMetadata metadata) {
	    this.metadata = metadata;
	}

	@Override
	public String getKeyInBundle() {
	    String key = metadata.getRandomValue();
	    key += metadata.getWidth() + "_" + metadata.getHeigth();
	    key += "." + metadata.getFormat().name().toLowerCase();
	    return key;
	}

    }
}

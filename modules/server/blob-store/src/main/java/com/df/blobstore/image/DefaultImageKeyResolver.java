package com.df.blobstore.image;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.df.blobstore.bundle.BundleKey;
import com.df.core.common.utils.ByteUtils;

public class DefaultImageKeyResolver implements ImageKeyResolver {

    private static Charset charset = Charset.forName("utf-8");

    @Override
    public ImageAttributes resolveImageAttributes(ImageKey imageKey) {
	byte[] bytes = ByteUtils.hexStringToBytes(imageKey.getKey());
	DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
	try {
	    int width = in.readInt();
	    int heigth = in.readInt();
	    int formatIntValue = in.readShort();
	    ImageFormat format = ImageFormat.fromIntValue(formatIntValue);
	    if (format == null) {
		throw new ImageStoreException("unknow or invalid image format:%s", formatIntValue);
	    }
	    ImageAttributes attributes = new ImageAttributes(width, heigth, format);
	    int nextLength = in.readInt();
	    if (nextLength == 0) {
		throw new ImageStoreException("image uniqueId is not specified in the key");
	    }
	    byte[] uniqueId = new byte[nextLength];
	    in.readFully(uniqueId);
	    attributes.setUniqueId(new String(uniqueId, charset));
	    nextLength = in.readInt();
	    if (nextLength == 0) {
		throw new ImageStoreException("image owner is not specified in the key");
	    }
	    byte[] owner = new byte[nextLength];
	    in.readFully(owner);
	    attributes.setOwner(new String(owner, charset));
	    return attributes;
	} catch (IOException ex) {
	    throw new ImageStoreException("Invalid image key, cannot resolve image attributes.");
	}
    }

    @Override
    public ImageKey hash(ImageAttributes metadata) {
	if (metadata.getOwner() == null) {
	    throw new ImageStoreException("Owner attribute is not specified");
	}
	if (metadata.getUniqueId() == null) {
	    throw new ImageStoreException("UniqueId attribute is not specified");

	}
	byte[] owner = metadata.getOwner().getBytes(charset);
	byte[] uniqueId = metadata.getUniqueId().getBytes(charset);
	int maxBufferSize = 512;
	int length = 4 * 2 + 2 + 4 + uniqueId.length + 4 + owner.length;
	if (length > maxBufferSize) {
	    throw new ImageStoreException("owner+uniqueId length canot exceed " + (512 - 4 * 6));
	}
	ByteBuffer buffer = ByteBuffer.allocate(length);
	buffer.putInt(metadata.getWidth());
	buffer.putInt(metadata.getHeigth());
	buffer.putShort((short) metadata.getFormat().intValue());
	buffer.putInt(uniqueId.length);
	buffer.put(uniqueId);
	buffer.putInt(owner.length);
	buffer.put(owner);
	return new ImageKey(ByteUtils.bytesToHexString(buffer.array()));
    }

    @Override
    public BundleKey resolveBundleKey(ImageKey imageKey) {
	ImageAttributes metadata = resolveImageAttributes(imageKey);
	return new ImageBundleKey(metadata);
    }
}

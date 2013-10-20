package com.df.blobstore.image;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.df.blobstore.bundle.BundleKey;

public class DefaultImageKeyResolver implements ImageKeyResolver {

    private static Charset utf8 = Charset.forName("utf-8");

    @Override
    public ImageAttributes resolveImageAttributes(ImageKey imageKey) {
	byte[] bytes = Base64.decodeBase64(imageKey.getKey());
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
	    attributes.setUniqueId(new String(uniqueId, utf8));
	    nextLength = in.readInt();
	    if (nextLength == 0) {
		throw new ImageStoreException("image owner is not specified in the key");
	    }
	    byte[] owner = new byte[nextLength];
	    in.readFully(owner);
	    attributes.setOwner(new String(owner, utf8));
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
	byte[] owner = metadata.getOwner().getBytes(utf8);
	byte[] uniqueId = metadata.getUniqueId().getBytes(utf8);
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
	return new ImageKey(Base64.encodeBase64URLSafeString(buffer.array()));
    }

    @Override
    public BundleKey resolveBundleKey(ImageKey imageKey) {
	ImageAttributes metadata = resolveImageAttributes(imageKey);
	return new ImageBundleKey(metadata);
    }
}

package com.df.blobstore.image;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;

public class ImageFactory {

    public static Image createImageFromStream(InputStream in, String tenantId) {
	ByteSourceInputStream byteSource = new ByteSourceInputStream(in, null);
	ImageInfo info = null;
	try {
	    info = Imaging.getImageInfo(byteSource.getInputStream(), null);
	} catch (ImageReadException ex) {
	    throw new ImageException(ex, "Invalid image format?");
	} catch (IOException ex) {
	    throw new ImageException(ex);
	}
	int width = info.getWidth();
	int height = info.getHeight();
	ImageFormat format = Enum.valueOf(ImageFormat.class, info.getFormat().getName());
	ImageAttributes attributes = new ImageAttributes(width, height, format);
	attributes.setOwner(tenantId);
	byte[] rawData;
	InputStream temp = null;
	try {
	    temp = byteSource.getInputStream();
	    rawData = ByteSource.getStreamBytes(temp);
	} catch (IOException ex) {
	    throw new ImageException(ex);
	} finally {
	    if (temp != null) {
		try {
		    temp.close();
		} catch (IOException e) {
		}
	    }
	}
	return new Image(attributes, rawData);
    }

    public static Image createBlankImage(ImageKeyResolver keyResolver, ImageKey key) {
	ImageAttributes metadata = keyResolver.resolveImageAttributes(key);
	return new Image(metadata);
    }
}

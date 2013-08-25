package com.df.blobstore.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.bytesource.ByteSourceInputStream;

public class ImageFactory {

    public Image createImageFromStream(InputStream in, String tenantId) {
	ByteSourceInputStream byteSource = new ByteSourceInputStream(in, null);
	BufferedImage image = null;
	ImageInfo info = null;
	try {
	    image = Imaging.getBufferedImage(byteSource.getInputStream());
	    info = Imaging.getImageInfo(byteSource.getInputStream(), null);
	} catch (ImageReadException ex) {
	    throw new ImageException(ex, "Invalid image format?");
	} catch (IOException ex) {
	    throw new ImageException(ex);
	}
	int width = info.getWidth();
	int height = info.getHeight();
	int bitDepth = info.getBitsPerPixel();
	ImageFormat format = Enum.valueOf(ImageFormat.class, info.getFormat().getName());
	ImageAttributes attributes = new ImageAttributes(width, height, format);
	attributes.setOwner(tenantId);
	attributes.setBitDepth(bitDepth);
	byte[] rawData;
	try {
	    rawData = Imaging.writeImageToBytes(image, info.getFormat(), null);
	} catch (ImageWriteException ex) {
	    throw new ImageException(ex);
	} catch (IOException ex) {
	    throw new ImageException(ex);
	}
	return new Image(attributes, rawData);
    }

    public Image createBlankImage(ImageKeyResolver keyResolver, ImageKey key) {
	ImageAttributes metadata = keyResolver.resolveImageAttributes(key);
	return new Image(metadata);
    }
}

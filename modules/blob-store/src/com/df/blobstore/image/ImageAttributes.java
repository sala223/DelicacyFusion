package com.df.blobstore.image;

import java.io.Serializable;
import java.util.UUID;

public final class ImageAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    private int width;

    private int heigth;

    private int bitDepth;

    private ImageFormat format;

    private String owner;

    private String uniqueId;

    public ImageAttributes() {
	this.uniqueId = UUID.randomUUID().toString();
    }

    public ImageAttributes(int width, int heigth, ImageFormat format) {
	this.width = width;
	this.heigth = heigth;
	this.format = format;
	this.uniqueId = UUID.randomUUID().toString();
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public int getHeigth() {
	return heigth;
    }

    public void setHeigth(int heigth) {
	this.heigth = heigth;
    }

    public int getBitDepth() {
	return bitDepth;
    }

    public void setBitDepth(int bitDepth) {
	this.bitDepth = bitDepth;
    }

    public ImageFormat getFormat() {
	return format;
    }

    public void setFormat(ImageFormat format) {
	this.format = format;
    }

    public String getOwner() {
	return this.owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    public void setUniqueId(String uniqueId) {
	this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
	return this.uniqueId;
    }

}

package com.df.blobstore.image;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class ImageMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    private int width;

    private int heigth;

    private int bitDepth;

    private ImageFormat format;

    private String owner;

    private Map<String, String> extraInformation = new HashMap<String, String>();

    public static final String REALM = "com.df.blobstore.image.realm";

    public static final String RANDOM_VALUE = "com.df.blobstore.image.random_value";

    public ImageMetadata() {
    }

    public ImageMetadata(int width, int heigth, ImageFormat format) {
	this.width = width;
	this.heigth = heigth;
	this.format = format;
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
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
    }

    public String getRealm() {
	return extraInformation.get(REALM);
    }

    public String getRandomValue() {
	return extraInformation.get(RANDOM_VALUE);
    }

    public void addExtraInformation(String key, String value) {
	extraInformation.put(key, value);
    }

    public String getExtraInformation(String key) {
	return extraInformation.get(key);
    }

    public String[] getExtraInformationKeys() {
	return extraInformation.keySet().toArray(new String[0]);
    }
}

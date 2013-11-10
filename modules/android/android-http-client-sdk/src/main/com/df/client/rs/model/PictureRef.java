package com.df.client.rs.model;

import java.io.Serializable;

public class PictureRef implements Serializable {

    private static final long serialVersionUID = 1L;

    private String imageId;

    private String imageLink;

    private int width;

    private int heigth;

    private String format;

    public String getImageId() {
	return imageId;
    }

    public void setImageId(String imageId) {
	this.imageId = imageId;
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

    public String getFormat() {
	return format;
    }

    public void setFormat(String format) {
	this.format = format;
    }

    public String getImageLink() {
	return imageLink;
    }

    public void setImageLink(String imageLink) {
	this.imageLink = imageLink;
    }
}

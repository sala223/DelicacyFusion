package com.df.masterdata.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class PictureRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 512, nullable = false, name = "IMG_ID")
    private String imageId;

    @Transient
    private String imageLink;

    @Column(name = "WIDTH")
    private int width;

    @Column(name = "HEIGTH")
    private int heigth;

    @Column(length = 16, nullable = false, name = "FORMAT")
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

package com.df.blobstore.image;

public enum ImageFormat {

    JPEG("jpg"), PNG("png"), TIF("tif"), GIF("gif");

    private String shortName;

    private ImageFormat(String shortName) {
	this.shortName = shortName;
    }

    public static ImageFormat fromShortName(String shortName) {
	for (ImageFormat type : values()) {
	    if (type.shortName == shortName) {
		return type;
	    }
	}
	return null;
    }

    public String shortName() {
	return shortName;
    }
}
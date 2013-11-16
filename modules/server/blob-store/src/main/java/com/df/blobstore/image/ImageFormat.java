package com.df.blobstore.image;

public enum ImageFormat {

	JPEG(1), PNG(2), TIF(3), GIF(4);

	private int intValue;

	private ImageFormat(int intValue) {
		this.intValue = intValue;
	}

	public static ImageFormat fromIntValue(int intValue) {
		for (ImageFormat type : values()) {
			if (type.intValue == intValue) {
				return type;
			}
		}
		return null;
	}

	public String getMediaType() {
		return "image/" + this.name().toLowerCase();
	}

	public String getFileSuffix() {
		if (this == JPEG) {
			return ".jpg";
		} else {
			return "." + this.name().toLowerCase();
		}
	}

	public int intValue() {
		return intValue;
	}
}
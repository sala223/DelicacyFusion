package com.df.blobstore.image;

public class ImageStoreException extends ImageException {

    private static final long serialVersionUID = 1L;

    public ImageStoreException(Throwable cause) {
	super(cause);
    }

    public ImageStoreException(Throwable throwable, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), throwable);
    }

    public ImageStoreException(String messageFormat, Object... args) {
	this(null, messageFormat, args);
    }

}

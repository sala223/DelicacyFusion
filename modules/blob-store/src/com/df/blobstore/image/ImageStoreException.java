package com.df.blobstore.image;

import com.df.blobstore.bundle.BlobStoreException;

public class ImageStoreException extends BlobStoreException {

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

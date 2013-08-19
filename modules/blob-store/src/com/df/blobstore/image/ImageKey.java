package com.df.blobstore.image;

import java.io.Serializable;

/**
 * A unique key to represents a image which is stored by blob store.
 */
public class ImageKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    public ImageKey(String key) {
	this.key = key;
    }

    public String getKey() {
	return key;
    }
}

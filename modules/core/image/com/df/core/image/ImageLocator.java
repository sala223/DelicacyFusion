package com.df.core.image;

public final class ImageLocator {

    private String realm;

    private String guid;

    public ImageLocator(String realm, String guid) {
	this.realm = realm;
	this.guid = guid;
    }

    public String getRealm() {
	return realm;
    }

    public String getGuid() {
	return guid;
    }
}

package com.df.client.rs.model;

import java.io.Serializable;

public class PictureRef implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rawImageId;

    private String scale1ImageId;

    private String scale2ImageId;

    private String scale3ImageId;

    private String comments;

    public String getRawImageId() {
	return rawImageId;
    }

    public void setRawImageId(String rawImageId) {
	this.rawImageId = rawImageId;
    }

    public String getScale1ImageId() {
	return scale1ImageId;
    }

    public void setScale1ImageId(String scale1ImageId) {
	this.scale1ImageId = scale1ImageId;
    }

    public String getScale2ImageId() {
	return scale2ImageId;
    }

    public void setScale2ImageId(String scale2ImageId) {
	this.scale2ImageId = scale2ImageId;
    }

    public String getScale3ImageId() {
	return scale3ImageId;
    }

    public void setScale3ImageId(String scale3ImageId) {
	this.scale3ImageId = scale3ImageId;
    }

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }
}

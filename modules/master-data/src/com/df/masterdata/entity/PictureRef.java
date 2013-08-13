package com.df.masterdata.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PictureRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 512, nullable = false)
    private String url;

    @Column(length = 128)
    private String comments;

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getComments() {
	return comments;
    }

    public void setComments(String comments) {
	this.comments = comments;
    }
}

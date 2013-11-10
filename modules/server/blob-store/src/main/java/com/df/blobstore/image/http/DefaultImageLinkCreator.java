package com.df.blobstore.image.http;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

public class DefaultImageLinkCreator implements ImageLinkCreator {

	@Autowired
	private ServletContext servletContext;

	private String imageRequestPrefix;

	public DefaultImageLinkCreator() {
	}

	public void setImageRequestPrefix(String imageRequestPrefix) {
		this.imageRequestPrefix = imageRequestPrefix;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public String createImageLink(String imageKey) {
		if (imageRequestPrefix != null) {
			return imageRequestPrefix + "/" + imageKey;
		} else {
			return "/" + imageKey;

		}
	}
}

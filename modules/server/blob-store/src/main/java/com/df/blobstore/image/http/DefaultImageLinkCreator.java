package com.df.blobstore.image.http;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

public class DefaultImageLinkCreator implements ImageLinkCreator {

    @Autowired
    private ServletContext servletContext;

    public DefaultImageLinkCreator() {
    }

    public void setServletContext(ServletContext servletContext) {
	this.servletContext = servletContext;
    }

    @Override
    public String createImageLink(String imageKey) {
	return servletContext.getContextPath() + "/" + imageKey;
    }
}
